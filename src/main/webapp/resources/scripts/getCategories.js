function getCategoriesRequest(urlVar) {
    $.ajax({
        url: urlVar,
        dataType: 'json',
        success: function (data, status, settings) {
            getCategories(data);
        }
    })
}

function getCategories(data) {
    var arr = data;
    for (i = 0; i < arr.length; i++){
        var category = arr[i];
        var opt = document.createElement('option');
        opt.innerHTML = category['categoryName'];
        opt.value = category['categoryNumber'];
        document.getElementById('category').appendChild(opt);
    }
}
