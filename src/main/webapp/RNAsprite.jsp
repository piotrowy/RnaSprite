 <!DOCTYPE html>
<html lang="pl">
<head>
    <title>RNAsprite</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="./styles.css">
    <script src="scripts.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script>
    $(document).ready(function(){
        $("#aboutApplication").click(function(){
            document.getElementById("tableTorsionAngles").innerHTML = null;
            document.getElementById("tableDistanceMatrix").innerHTML = null;
            popup();
        });
    });
</script>
    
<script>
    $(document).ready(function(){
        $("#getTorsionAngles").click(function(){
            document.getElementById("tableTorsionAngles").innerHTML = null;
            var dataIn = "structurePDB=";
            var pdbId = document.getElementById("pdbIdTAM").value;
            var fileIn = document.getElementById("modelTAM");
            if(pdbId != null && pdbId != ""){
                $.get("http://localhost:8080/RNAsprite/app/options/downloadTorsionAnglesFromPdb", dataIn+pdbId, function(data, status){
                    document.getElementById("tableTorsionAngles").appendChild(createTorsionAngleMatrixFromData(data));
                });
            } else if ("" == "") {
                
                var dataI = new FormData();
    dataI.append('file-'+1, fileIn.files[0]);
                
                $.post("http://localhost:8080/RNAsprite/app/options/downloadTorsionAnglesFromFile", "modelFile="+fileIn.files[0], function(data, status){
                    console.log("wow");
                    //document.getElementById("tableTorsionAngles").appendChild(createTorsionAngleMatrixFromData(data));
                });
            } else {
               alert("Fill pdbId input");   
            }
        });
    });
</script>
    
<script>
    $(document).ready(function(){
        $("#displayDM").click(function(){
            document.getElementById("tableDistanceMatrix").innerHTML = null;
            var dataIn = "structurePDB=";
            var pdbId = document.getElementById("pdbIdDM").value;
            dataIn += pdbId + "&chain=";
            var chain = document.getElementById("chainId").value;
            dataIn += chain + "&at1=";
            var at1 = document.getElementById("atom1Id").value;
            dataIn += at1 + "&at2=";
            var at2 = document.getElementById("atom2Id").value;
            dataIn += at2;
            console.log(dataIn);
            if(pdbId != null && pdbId != ""){
                $.get("http://localhost:8080/RNAsprite/app/options/downloadDistanceMatrixFromPdb",dataIn,function(data, status){
                    document.getElementById("tableDistanceMatrix").appendChild(createDistanceMatrixFromData(data, at1, at2));
                });
            } else {
                alert("Fill pdbId input");
            }
        });
    });
</script>
    
<script>
    $(document).ready(function(){
        $("#getDistanceMatrix").click(function(){
            document.getElementById("tableDistanceMatrix").innerHTML = null;
            var pdbId = document.getElementById("pdbIdDM").value;
            if(pdbId != null && pdbId != ""){
                $("#inputDM").fadeIn(); 
                $.get("http://localhost:8080/RNAsprite/app/options/getAtomsList",function(data, status){
                    document.getElementById("tableDistanceMatrix").innerHTML = null;
                    document.getElementById("atom1Id").value = "";
                    document.getElementById("atom2Id").value = "";
                    dataList = document.getElementById("atoms-datalist");
                    var option = document.createElement("option");
                    option.value = "";
                    dataList.appendChild(option);
                    for(var index in data.list){
                        option = document.createElement("option");
                        option.value = data.list[index];
                        dataList.appendChild(option);
                    }
                });
                var dataIn = "structurePDB=";
                $.get("http://localhost:8080/RNAsprite/app/options/getChainsList",dataIn+pdbId,function(data, status){
                    document.getElementById("tableDistanceMatrix").innerHTML = null;
                    document.getElementById("chainId").value = "";
                    dataList = document.getElementById("chains-datalist");
                    var option = document.createElement("option");
                    option.value = "";
                    dataList.appendChild(option);
                    for(var index in data.list){
                        option = document.createElement("option");
                        option.value = data.list[index];
                        dataList.appendChild(option);
                    }
                });
            } else {
                alert("Fill pdbId input"); 
            }
        });
    });
</script>
</head> 
<body>
    <div class="container">
            <div class="page-header">
              <h1 class="text-primary">RNAsprite: RNA structural data calculator</h1>
            </div>
    </div>

    <div class="container">
        <div class="row">
            <ul class="nav nav-pills nav-justified">
                <li class="active"><a data-toggle="tab" id="aboutApplication" href="#description">About application</a></li>
                <li><a data-toggle="tab" id="tAM" href="#torsAngMatrix">Torsion angles matrix</a></li>
                <li><a data-toggle="tab" id="dM" href="#distMatrix">Distance matrix</a></li>
            </ul>
            <hr>
        </div>
    </div>
    <br>
    <div class="tab-content">
        <div id="description" class="tab-pane fade in active">
            <div class="row">
                <div class="container">
                     <h1>Tu będzie opis tego jak uzywac aplikacji.</h1>
                </div>
            </div>
        </div>
        <div id="torsAngMatrix" class="tab-pane fade">
            <div class="row">
                <div class="container">
                    <h2>Torsion angles matrix</h2>
                    <br>
                    <div class="col-sm-3">
                         <input type="text" class="form-control" id="pdbIdTAM" placeholder="Insert PdbID">
                    </div>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" placeholder="Insert file" id="fileTAM"/>
                        <input type="file" class="form-control" id="modelTAM"/>
                    </div>
                    <div class="col-sm-3">
                        <button type="button" id="getTorsionAngles" class="btn btn-default btn-block">Display torsion angles matrix</button> 
                    </div>
                    <div class="col-sm-3">
                        <button type="button" data-toggle="collapse" data-target="#emailRowTAM" class="btn btn-default btn-block">Send email</button>
                    </div>
                </div>
            </div>
            <div class="collapse" id="emailRowTAM">
                <div class="row">
                    <div class="container">
                        <h4>Email address</h4>
                        <div class="col-sm-6">
                            <input type="email" class="form-control" id="emailTAM">
                        </div>
                        <div class="col-sm-6">
                            <button type="submit" class="btn btn-default">Submit</button>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <div id="tableTorsionAngles" class="container"></div>
        </div>
        <div id="distMatrix" class="tab-pane fade">
            <div class="row">
                <div class="container">
                    <h2>Distance matrix</h2>  
                    <br>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" id="pdbIdDM" placeholder="Insert PdbID">
                    </div>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" placeholder="Insert file" id="fileDM"/>
                        <input type="file" class="form-control" id="modelDM"/>
                    </div>
                    <div class="col-sm-3">
                        <button type="button" id="getDistanceMatrix" class="btn btn-default btn-block">Upload structure</button> 
                    </div>
                    <div class="col-sm-3">
                        <button type="button" data-toggle="collapse" data-target="#emailRowDM" class="btn btn-default btn-block">Send email</button>
                    </div>
                </div>
            </div>
            <div id="inputDM" class="row" style="display:none">
                <div class="container">
                    <br>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" id="chainId" list="chains-datalist" placeholder="Insert chain ID">
                        <datalist id="chains-datalist"></datalist>
                    </div>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" id="atom1Id" list="atoms-datalist" placeholder="Insert atom 1 ID">
                    </div>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" id="atom2Id" list="atoms-datalist" placeholder="Insert atom 2 ID">
                    </div>
                    <div class="col-sm-3">
                        <button type="button" id="displayDM" class="btn btn-default btn-block">Display distance matrix</button> 
                    </div>
                    <datalist id="atoms-datalist"></datalist>
                </div>
            </div>
            <div class="collapse" id="emailRowDM">
                <div class="row">
                    <div class="container">
                        <h4>Email address</h4>
                        <div class="col-sm-6">
                            <input type="email" class="form-control" id="emailDM">
                        </div>
                        <div class="col-sm-6">
                            <button type="submit" class="btn btn-default">Submit</button>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <div id="tableDistanceMatrix" class="container"></div>
        </div>
    </div>

    <script>
        var wrapper = $('<div id="tam"/>').css({height:0,width:0,'overflow':'hidden'});
        var fileInputTAM = $(document.getElementById("modelTAM")).wrap(wrapper);

        fileInputTAM.change(function(){
            $this = $(this);
            $('#fileTAM').text($this.val());
            document.getElementById("fileTAM").value = document.getElementById("modelTAM").files[0].name;
        }).show();

        $('#fileTAM').click(function(){
            fileInputTAM.click();
        }).show();
    </script>    

    <script>
        var wrapper = $('<div id="dm"/>').css({height:0,width:0,'overflow':'hidden'});
        var fileInputDM = $(document.getElementById("modelDM")).wrap(wrapper);

        fileInputDM.change(function(){
            $this = $(this);
            $('#fileDM').text($this.val());
            document.getElementById("fileDM").value = document.getElementById("modelDM").files[0].name;
        }).show();

        $('#fileDM').click(function(){
            fileInputDM.click();
        }).show();
    </script> 

</body>
</html>

<!-- Przerzucić generowanie widoku na klienta. -->
<!-- Kolorowanie tabeli w kliencie -->
<!-- Porozdzielać moduły css, scripts, html -->
<!-- Zamienić na obiektowy html -->
<!-- Dać użytkownikowi możliwość definiowania włąsnego kąta  -->