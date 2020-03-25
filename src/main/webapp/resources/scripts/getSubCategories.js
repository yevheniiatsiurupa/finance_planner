function getSubcategories(urlVar) {
    deleteOptions();
    var cat =  document.getElementById('category');
    var categoryVal = cat.options[cat.selectedIndex].text;
    $.ajax({
        type: 'POST',
        url: urlVar,
        data: categoryVal,
        contentType: "text/plain",
        datatype: 'json',
        success: function (data, status, settings) {
            var arr = data;
            for (var i = 0; i < arr.length; i++) {
                var subcategory = arr[i];
                var opt = document.createElement('option');
                opt.innerHTML = subcategory['subCategoryName'];
                opt.value = subcategory['subCategoryNumber'];
                document.getElementById('subCategory').appendChild(opt);
            }
        }
    })
}

function deleteOptions() {
    var parent = document.getElementById('subCategory');
    var nodes = parent.childNodes;
    for (var i = 0; i < nodes.length; i++) {
        var elem = nodes[i];
        if (elem.value !== '') {
            parent.removeChild(elem);
            i--;
        }
    }
}