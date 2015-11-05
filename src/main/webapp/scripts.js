function createCell(text) {
    var cell = document.createElement("td"),
        cellText = document.createTextNode(text);
    cell.appendChild(cellText);
    return cell;
}

function createTorsionAngleMatrixFromData(data) {
    var container = document.createElement("div");
    container.setAttribute("class", "table-responsive");
    for (var index in data.matrix.model) {
        var table = document.createElement("table");
        table.setAttribute("class", "myTable");
        var tableHead = document.createElement("thead"),
            tableBody = document.createElement("tbody"),
            hTr = document.createElement("tr"),
            hTh = document.createElement("th");
        hTh.setAttribute("colspan", data.angleNames.length + 3);
        var modelName = document.createTextNode(data.matrix.model[index].value[0].model + " " +
                                                data.matrix.model[index].value[0].chain);
        hTh.appendChild(modelName);
        table.appendChild(tableHead);
        tableHead.appendChild(hTr);
        hTr.appendChild(hTh);

        var angleTr = document.createElement("tr");
        angleTr.appendChild(createCell("PDB name"));
        angleTr.appendChild(createCell("Symbol"));
        angleTr.appendChild(createCell("Res. no."));

        for (var i in data.angleNames) {
            angleTr.appendChild(createCell(data.angleNames[i]));
        }
        tableBody.appendChild(angleTr);
        for (var i in data.matrix.model[index].value) {
            var bTr = document.createElement("tr");
            bTr.appendChild(createCell(data.matrix.model[index].value[i].pdbName));
            bTr.appendChild(createCell(data.matrix.model[index].value[i].symbol));
            bTr.appendChild(createCell(data.matrix.model[index].value[i].resNo));
            for(var j in data.matrix.model[index].value[i].row){
                bTr.appendChild(createCell(data.matrix.model[index].value[i].row[j]));
            }
            tableBody.appendChild(bTr);
        }
        table.appendChild(tableBody);
        container.appendChild(table);
        }
    return container;
}

function createDistanceMatrixFromData(data, at1, at2){
    var container = document.createElement("div");
    container.setAttribute("class", "table-responsive");
    for(var index in data.matrix.model){
        var table = document.createElement("table");
        table.setAttribute("class", "myTable");
        var tableHead = document.createElement("thead"),
            tableBody = document.createElement("tbody"),
            hTr = document.createElement("tr"),
            hTh = document.createElement("th");
        hTh.setAttribute("colspan", data.matrix.model[index].value.length + 1);
        var modelName = document.createTextNode(data.matrix.model[index].value[0].model + " " +
                                                data.matrix.model[index].value[0].chain);
        table.appendChild(tableHead);
        tableHead.appendChild(hTr);
        hTr.appendChild(hTh);
        hTh.appendChild(modelName);

        var resTr = document.createElement("tr");
        resTr.appendChild(createCell(at1 + "\\" + at2));
        for(var i in data.matrix.model[index].value){
            resTr.appendChild(createCell(data.matrix.model[index].value[i].residue));
        }
        tableBody.appendChild(resTr);
        for(var i in data.matrix.model[index].value){
            var bTr = document.createElement("tr");
            bTr.appendChild(createCell(data.matrix.model[index].value[i].residue));
            for(var j in data.matrix.model[index].value[i].row){
                bTr.appendChild(createCell(data.matrix.model[index].value[i].row[j]));
            }
            tableBody.appendChild(bTr);
        }
        table.appendChild(tableBody);
        container.appendChild(table);
    }
    return container;
}