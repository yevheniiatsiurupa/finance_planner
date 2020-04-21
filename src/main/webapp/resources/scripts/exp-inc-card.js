function checkAndSubmit() {
    let catElem = document.getElementById("category");
    if (catElem.value === "") {
        catElem.className = "form-control is-invalid no-image";

        let catDiv = document.getElementById("category-group");
        catDiv.className = "form-group has-danger";

        let catError = document.getElementById("category-error");
        catError.className = "invalid-feedback";
    } else {
        catElem.className = "form-control";

        let catDiv = document.getElementById("category-group");
        catDiv.className = "form-group";

        let catError = document.getElementById("category-error");
        catError.className = "invalid-feedback invisible";
    }

    let subCatElem = document.getElementById("subCategory");
    if (subCatElem.value === "") {
        subCatElem.className = "form-control is-invalid no-image";

        let subCatDiv = document.getElementById("subCategory-group");
        subCatDiv.className = "form-group has-danger";

        let subCatError = document.getElementById("subCategory-error");
        subCatError.className = "invalid-feedback";
    } else {
        subCatElem.className = "form-control";

        let subCatDiv = document.getElementById("subCategory-group");
        subCatDiv.className = "form-group";

        let subCatError = document.getElementById("subCategory-error");
        subCatError.className = "invalid-feedback invisible";
    }

    if (catElem.value !== "" && subCatElem.value !== "") {
        var date = $("#created").datepicker('getDate');
        $("#created").val(date);

        let myForm = document.getElementById("my-form");
        myForm.submit();
    }

}