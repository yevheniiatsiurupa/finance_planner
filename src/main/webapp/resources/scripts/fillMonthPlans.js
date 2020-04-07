function fillExpensesPlan(urlVar) {
    $.ajax({
        url: urlVar,
        dataType: 'json',
        success: function (data, status, settings) {
            fillExpenses(data);
        }
    })
}

function fillExpenses(data) {
    let arr = data;
    let root = document.createElement('div');
    root.classList.add("plan-expense-block");
    let resultCat = [];
    let intro = `
    <div class="category-group">
          <div class="category-title">
              <div class="category-marker"></div>
              <div class="category-name">
              <span class="title-box">Планируемые расходы</span>
              </div>
          </div>
    </div>
    `;
    resultCat.push(intro);
    for (i = 0; i < arr.length; i++){
        let category = arr[i];
        let catNumber = category['categoryNumber'];
        let catId = "exp-cat-" + catNumber;
        let catHtml = `
        <div class="exp-cat">
            <div class="category-group" id="${catId}">
                 <div class="category-title">
                     <div class="category-marker">
                     *
                     </div>
                     <div class="category-name">
                     <span>${category['categoryName']}</span>
                     </div>
                 </div>
            </div>
            ${fillSubExpenses(category['subCategories'], catId)}
        </div>
        `;
        resultCat.push(catHtml);
    }
    root.innerHTML = resultCat.join('');

    let parentElem = document.getElementById('main');
    parentElem.insertBefore(root, parentElem.firstChild);
}

function fillSubExpenses(data, catId) {
    let arr = data;
    let result = [];
    for (j = 0; j < arr.length; j++){
        let subcategory = arr[j];
        let subcatNumber = subcategory['subCategoryNumber'];
        let subcatId = catId + "-subcat-" + subcatNumber + "_0";
        let subcatHtml = `
        <div class="subcategory" id="${subcatId}">
           <div class="subcat-name expense-color">
           ${subcategory['subCategoryName']}
           </div>
           <div class="subcat-value">
              <div class="subcat-value-text">
              <input name="exp-val" type="text" placeholder="0" onblur="updateTotalExpenses()"/>
              </div>
              <div class="subcat-value-append">
              <span>руб.</span>
              </div>
           </div>
           <div class="subcat-comment">
           <input type="text" placeholder="Комментарий"/>
           </div>
           <div class="button-div">
              <button type="button" class="button-box" onclick="addPlanElement('${subcatId}')" title="Копировать">
              <img class="button-add" src="../../resources/images/add-simple-white.png">
              </button>
           </div>
           <div class="button-div">
              <button type="button" class="button-box" onclick="deletePlanElement('${subcatId}')" title="Удалить">
              <img class="button-delete" src="../../resources/images/subtract-simple-white.png">
              </button>
           </div>
        </div>
        `;
        result.push(subcatHtml);
    }
    return result.join('');
}

function fillIncomesPlan(urlVar) {
    $.ajax({
        url: urlVar,
        dataType: 'json',
        success: function (data, status, settings) {
            fillIncomes(data);
        }
    })
}

function fillIncomes(data) {
    let arr = data;
    let root = document.createElement('div');
    root.classList.add("plan-income-block");
    let resultCat = [];
    let intro = `
    <div class="category-group">
          <div class="category-title">
              <div class="category-marker"></div>
              <div class="category-name">
              <span class="title-box">Планируемые доходы</span>
              </div>
          </div>
    </div>
    `;
    resultCat.push(intro);
    for (i = 0; i < arr.length; i++){
        let category = arr[i];
        let catNumber = category['categoryNumber'];
        let catId = "inc-cat-" + catNumber;
        let catHtml = `
        <div class="inc-cat">
            <div class="category-group" id="$catId">
                 <div class="category-title">
                     <div class="category-marker">
                     *
                     </div>
                     <div class="category-name">
                     <span>${category['categoryName']}</span>
                     </div>
                 </div>
            </div>
            ${fillSubIncomes(category['subCategories'], catId)}
        </div>
        `;
        resultCat.push(catHtml);
    }
    root.innerHTML = resultCat.join('');

    let parentElem = document.getElementById('main');
    parentElem.insertBefore(root, document.getElementById('right-menu'));
}

function fillSubIncomes(data, catId) {
    let arr = data;
    let result = [];
    for (j = 0; j < arr.length; j++){
        let subcategory = arr[j];
        let subcatNumber = subcategory['subCategoryNumber'];
        let subcatId = catId + "-subcat-" + subcatNumber + "_0";
        let subcatHtml = `
        <div class="subcategory" id="${subcatId}">
           <div class="subcat-name income-color">
           ${subcategory['subCategoryName']}
           </div>
           <div class="subcat-value">
              <div class="subcat-value-text">
              <input name="inc-val" type="text" placeholder="0" onblur="updateTotalIncomes()"/>
              </div>
              <div class="subcat-value-append">
              <span>руб.</span>
              </div>
           </div>
           <div class="subcat-comment">
           <input type="text" placeholder="Комментарий"/>
           </div>
           <div class="button-div">
              <button type="button" class="button-box" onclick="addPlanElement('${subcatId}')" title="Копировать">
              <img class="button-add" src="../../resources/images/add-simple-white.png">
              </button>
           </div>
           <div class="button-div">
              <button type="button" class="button-box" onclick="deletePlanElement('${subcatId}')" title="Удалить">
              <img class="button-delete" src="../../resources/images/subtract-simple-white.png">
              </button>
           </div>
        </div>
        `;
        result.push(subcatHtml);
    }
    return result.join('');
}

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
    return {
        name: planName,
        comment: planComment,
        startDate: startDate,
        endDate: endDate,
        expenses: expenseObjects,
        incomes: incomeObjects
    };
}


function savePlan() {
    let planObj = createShortPlanObject();

    $.ajax({
        url: window.location.href,
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify(planObj),
        dataType: 'text',
        success: function (data, status, settings) {
            console.log(data);
            showMessage(data);

        }
    })
}

function showMessage(data) {
    document.getElementById("main").innerHTML = `
            <div class="col-sm-7">
                <div class="row">
                    <h5>${data}</h5>
                </div>
                <br/>
                <div class="row">
                    <form action="../../"  method="get">
                        <button type="submit" class="btn btn-outline-primary">На главную</button>
                    </form>
                </div>
            </div>
            `;
}