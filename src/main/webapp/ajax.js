var data = {
    "modelSpecifications": {
        "0": {
            "modelNumber" : 0,
            "chainSpecifications" : {
                "A" : ["DELTA", "BETA", "GAMMA"]
            }
        }
    }
};

$.ajax({
    type: "POST",
    contentType : 'application/json; charset=utf-8',
    dataType : 'json',
    url: "http://localhost:8080/matrix/torsion-angles/a583da56-1376-4c93-b81b-7475cad7d112",
    data: JSON.stringify(data), // Note it is important
    success :function(result) {
        console.log(result);
    }
});

var data = {
	"modelSpecifications": {
        "0": {
            "modelNumber" : 0,
            "chainSpecifications" : {
                "A" : {
                    "residues": [],
                    "atoms": {
                        "first": "OP1",
                        "second": "C1'"
                    }
                }
            }
        }
    }
};

$.ajax({
    type: "POST",
    contentType : 'application/json; charset=utf-8',
    dataType : 'json',
    url: "http://localhost:8080/matrix/distances/b910492e-6989-45bf-aa3e-ce9765b36b55",
    data: JSON.stringify(data), // Note it is important
    success :function(result) {
        console.log(result);
    }
});
