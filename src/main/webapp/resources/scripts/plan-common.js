function addPlanElement(subcatId) {
    let root = document.getElementById(subcatId);
    let disabledElem = root.getElementsByClassName("disable-color");
    if (disabledElem.length > 0) {
        enableSubcategory(subcatId);
        updateTotalIncomes();
        updateTotalExpenses();
        return;
    }
    let rootId = root.getAttribute('id');
    let rootIdBase = rootId.split('_')[0];
    let rootIdSuff = rootId.split('_')[1];

    let nextSib = document.getElementById(subcatId).nextElementSibling;

    let newId = '';
    let newSuffix = '';

    if (nextSib == null) {
        newSuffix = Number(rootIdSuff) + 1;
        newId = rootIdBase + "." + newSuffix;
    } else {
        let sibId = nextSib.getAttribute('id');
        let sibIdBase = sibId.split('_')[0];
        let sibIdSuff = sibId.split('_')[1];

        if (rootIdBase === sibIdBase) {
            let suffNumb = (Number(rootIdSuff) + Number(sibIdSuff)) / 2;
            newSuffix = Math.round(suffNumb * 100) / 100;
        } else {
            newSuffix = Number(rootIdSuff) + 1;
        }
        newId = rootIdBase + "_" + newSuffix;
    }

    let clone = document.getElementById(subcatId).cloneNode(true);
    clone.setAttribute('id', newId);
    let buttons = clone.getElementsByTagName('button');
    let buttonAdd = buttons[0];
    let buttonAddClick = "addPlanElement('" + newId + "')";
    buttonAdd.setAttribute('onclick', buttonAddClick);

    let buttonDelete = buttons[1];
    let buttonDeleteClick = "deletePlanElement('" + newId + "')";
    buttonDelete.setAttribute('onclick', buttonDeleteClick);

    root.parentNode.insertBefore(clone, root.nextSibling);
    updateTotalIncomes();
    updateTotalExpenses();
}

function deletePlanElement(subcatId) {
    let root = document.getElementById(subcatId);

    let checkNext = checkNextElem(subcatId);
    let checkPrev = checkPrevElem(subcatId);

    if (checkNext || checkPrev) {
        root.parentNode.removeChild(root);
    } else {
        disableSubcategory(subcatId);
    }
    updateTotalIncomes();
    updateTotalExpenses();
}

function checkNextElem(subcatId) {
    let root = document.getElementById(subcatId);
    let rootId = root.getAttribute('id');
    let rootIdBase = rootId.split('_')[0];

    let nextSib = document.getElementById(subcatId).nextElementSibling;
    let nextOk = true;
    if (nextSib == null) {
        nextOk = false;
    } else {
        let nextSibId = nextSib.getAttribute('id');
        let nextSibIdBase = nextSibId.split('_')[0];
        if (nextSibIdBase !== rootIdBase) {
            nextOk = false;
        }
    }
    return nextOk;
}

function checkPrevElem(subcatId) {
    let root = document.getElementById(subcatId);
    let rootId = root.getAttribute('id');
    let rootIdBase = rootId.split('_')[0];

    let prevSib = document.getElementById(subcatId).previousElementSibling;
    let prevOk = true;
    if (prevSib == null) {
        prevOk = false;
    } else {
        let prevSibId = prevSib.getAttribute('id');
        let prevSibIdBase = prevSibId.split('_')[0];
        if (prevSibIdBase !== rootIdBase) {
            prevOk = false;
        }
    }
    return prevOk;
}

function disableSubcategory(subcatId) {
    let root = document.getElementById(subcatId);

    let inputs = root.getElementsByTagName("input");
    for (let i = 0; i < inputs.length; i++) {
        let input = inputs[i];
        input.disabled = true;
    }

    let nameElems = root.getElementsByClassName("subcat-name");
    for (let i = 0; i < nameElems.length; i++) {
        let nameElem = nameElems[i];
        nameElem.className = "subcat-name disable-color";
    }

    let buttons = root.getElementsByClassName("button-box");
    let button = buttons[1];
    button.disable = true;

    let appends = root.getElementsByClassName("subcat-value-append");
    let append = appends[0];
    append.className = "subcat-value-append disable-color"
}

function enableSubcategory(subcatId) {
    let root = document.getElementById(subcatId);

    let inputs = root.getElementsByTagName("input");
    for (let i = 0; i < inputs.length; i++) {
        let input = inputs[i];
        input.disabled = false;
    }

    let nameElems = root.getElementsByClassName("subcat-name");
    for (let i = 0; i < nameElems.length; i++) {
        let nameElem = nameElems[i];
        if (subcatId.startsWith('exp')) {
            nameElem.className = "subcat-name expense-color";
        }
        if (subcatId.startsWith('inc')) {
            nameElem.className = "subcat-name income-color";
        }
    }

    let buttons = root.getElementsByClassName("button-box");
    let button = buttons[1];
    button.disable = false;

    let appends = root.getElementsByClassName("subcat-value-append");
    let append = appends[0];
    append.className = "subcat-value-append"
}

function updateTotalExpenses() {
    let arr = document.getElementsByName('exp-val');
    let tot = 0;
    for(let i = 0;i<arr.length;i++){
        if (!arr[i].disabled) {
            if(parseInt(arr[i].value))
                tot += parseInt(arr[i].value);
        }
    }
    document.getElementById('expenses-total').innerText = String(tot);

    let incomes = document.getElementById('incomes-total').innerText;
    let difference = incomes - tot;
    document.getElementById('difference').innerText = String(difference);
}
function updateTotalIncomes() {
    let arr = document.getElementsByName('inc-val');
    let tot = 0;
    for(let i = 0;i<arr.length;i++){
        if (!arr[i].disabled) {
            if(parseInt(arr[i].value))
                tot += parseInt(arr[i].value);
        }
    }
    document.getElementById('incomes-total').innerText = String(tot);

    let expenses = document.getElementById('expenses-total').innerText;
    let difference = tot - expenses;
    document.getElementById('difference').innerText = String(difference);
}

function getCategoryName(cat) {
    let nameElem = cat.getElementsByClassName("category-name")[0];
    return nameElem.children[0].innerText;
}

function getSubcategoryName(subcat) {
    return subcat.getElementsByClassName("subcat-name")[0].innerText;
}

function getSubcategoryValue(subcat) {
    return subcat.getElementsByClassName("subcat-value-text")[0].children[0].value;
}

function getSubcategoryComment(subcat) {
    return subcat.getElementsByClassName("subcat-comment")[0].children[0].value;
}

function makeSubcatObject(subcat) {
    let subcatName = getSubcategoryName(subcat);
    let subcatValue = getSubcategoryValue(subcat);
    let subcatComment = getSubcategoryComment(subcat);
    return {
        amount: subcatValue,
        comment: subcatComment,
        subCategoryName: subcatName,
        categoryName: ''
    };
}

function getPlanName() {
    return document.getElementById("plan-name").value;
}

function getPlanComment() {
    return document.getElementById("plan-comment").value;
}

function getPlanDate(dateId) {
    return $(dateId).datepicker('getDate');
}

function getPlanTotalExp(totalId) {
    return document.getElementById(totalId).innerText;
}

function getSubcatObjects(catClassName) {
    let subcatObjects = [];
    let cats = document.getElementsByClassName(catClassName);
    for (let i = 0; i < cats.length; i++) {
        let cat = cats[i];
        let catName = getCategoryName(cat);
        let subcats = cat.getElementsByClassName("subcategory");
        for (let j = 0; j < subcats.length; j++) {
            let subcat = subcats[j];
            let subcatObj = makeSubcatObject(subcat);
            if (subcatObj.amount !== '' && subcatObj.amount !== '0') {
                subcatObj.categoryName = catName;
                subcatObjects.push(subcatObj);
            }
        }
    }
    return subcatObjects;
}



function createShortPlanObject() {
    let expenseObjects = getSubcatObjects("exp-cat");
    let incomeObjects = getSubcatObjects("inc-cat");
    let planName = getPlanName();
    let planComment = getPlanComment();
    let startDate = getPlanDate("#start-date");
    let endDate = getPlanDate("#end-date");
    let totalExp = getPlanTotalExp("expenses-total");
    let totalInc = getPlanTotalExp("incomes-total");
    return {
        name: planName,
        comment: planComment,
        startDate: startDate,
        endDate: endDate,
        expenses: expenseObjects,
        incomes: incomeObjects,
        totalExpenses: totalExp,
        totalIncomes: totalInc
    };
}


function showMessage(data) {
    document.getElementById("main").innerHTML = `
            <div class="col-sm-7">
                <div class="main-box">
                    <div class="row">
                        <h5>${data['message']}</h5>
                    </div>
                    <br/>
                    <div class="row">
                        <form action="../../short-plan"  method="get">
                            <button type="submit" class="btn btn-outline-primary">${data['button1']}</button>
                        </form>
                    </div>
                    <div class="spacing"></div>
                    <div class="row">
                        <form action="../../"  method="get">
                            <button type="submit" class="btn btn-outline-primary">${data['button2']}</button>
                        </form>
                    </div>
                </div>
            </div>
            `;
}