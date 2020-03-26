function reloadWithFilters() {
    var requestParams = "?";
    if (!document.getElementById("amountMin").disabled) {
        requestParams += "amountMin=" + document.getElementById("amountMin").value + "&";
    }
    if (!document.getElementById("amountMax").disabled) {
        requestParams += "amountMax=" + document.getElementById("amountMax").value + "&";
    }
    if (document.getElementById("category").value !== "") {
        requestParams += "categoryNumber=" + document.getElementById("category").value + "&";
    }
    if (document.getElementById("subCategory").value !== "") {
        requestParams += "subCategoryNumber=" + document.getElementById("subCategory").value + "&";
    }
    if (!document.getElementById("createdMin").disabled) {
        var date = $("#createdMin").datepicker('getDate');
        var formatDate = $.datepicker.formatDate("dd-mm-yy", date);
        requestParams += "createdMin=" + formatDate + "&";
    }
    if (!document.getElementById("createdMax").disabled) {
        var date2 = $("#createdMax").datepicker('getDate');
        var formatDate2 = $.datepicker.formatDate("dd-mm-yy", date2);
        requestParams += "createdMax=" + formatDate2 + "&";
    }

    var comment = document.querySelector('input[name="commentRadio"]:checked').value;
    if (comment !== "") {
        requestParams += "comment=" + comment + "&";
    }
    if (document.querySelector('input[name="paymentRadio"]:checked') != null) {
        var cache = document.querySelector('input[name="paymentRadio"]:checked').value;
        if (cache !== "") {
            requestParams += "cache=" + cache + "&";
        }
    }

    if (requestParams.length > 1) {
        requestParams = requestParams.substring(0, requestParams.length - 1);
    }

    var url = window.location.href.split('?')[0];
    window.location.href = url + requestParams;
}
function reloadWithSort(sortVar, dir) {
    var prevUrl = window.location.href;
    if (!prevUrl.includes("?")) {
        window.location.href = prevUrl + "?sort=" + sortVar + "," + dir;
    }
    var url = window.location.href.split("?")[0];
    var params = window.location.href.split("?")[1];
    if (params === "") {
        window.location.href = prevUrl + "sort=" + sortVar + "," + dir;
    }
    if (params.includes("sort=")) {
        var arr = params.split("&");
        for (i = 0; i < arr.length; i++) {
            if (arr[i].includes("sort=")) {
                arr[i] = "sort=" + sortVar + "," + dir;
            }
        }
        window.location.href = url + "?" + arr.join("&");
    } else {
        window.location.href = prevUrl + "&sort=" + sortVar + "," + dir;
    }
}
function reloadWithPage(pageNumber) {
    var prevUrl = window.location.href;
    if (!prevUrl.includes("?")) {
        window.location.href = prevUrl + "?page=" + pageNumber;
    }
    var url = window.location.href.split("?")[0];
    var params = window.location.href.split("?")[1];
    if (params === "") {
        window.location.href = prevUrl + "page=" + pageNumber;
    }
    if (params.includes("page=")) {
        var arr = params.split("&");
        for (i = 0; i < arr.length; i++) {
            if (arr[i].includes("page=")) {
                arr[i] = "page=" + pageNumber;
            }
        }
        window.location.href = url + "?" + arr.join("&");
    } else {
        window.location.href = prevUrl + "&page=" + pageNumber;
    }
}