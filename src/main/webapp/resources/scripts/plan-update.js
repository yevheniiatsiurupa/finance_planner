function loadPlanForUpdate() {
    let urlParts = window.location.href.split("?")[0].split("/");
    let id = urlParts[urlParts.length - 1];
    $.ajax({
        url: "../show/expenses-update",
        type: 'post',
        contentType: 'text/plain',
        data: id,
        dataType: 'json',
        success: function (data, status, settings) {
            fillExpensesForUpdate(data);
            disableEmptyItems("plan-expense-block");
        }
    });
    $.ajax({
        url: "../show/incomes-update",
        type: 'post',
        contentType: 'text/plain',
        data: id,
        dataType: 'json',
        success: function (data, status, settings) {
            fillIncomesForUpdate(data);
            disableEmptyItems("plan-income-block");
        }
    });
}
function fillExpensesForUpdate(data) {
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
        let itemList = arr[i];
        let item = itemList[0];
        let catNumber = item['categoryNumber'];
        let catId = "exp-cat-" + catNumber;
        let catHtml = `
        <div class="exp-cat">
            <div class="category-group" id="${catId}">
                 <div class="category-title">
                     <div class="category-marker">
                     *
                     </div>
                     <div class="category-name">
                     <span>${item['categoryName']}</span>
                     </div>
                 </div>
            </div>
            ${fillSubExpensesForUpdate(itemList, catId)}
        </div>
        `;
        resultCat.push(catHtml);
    }
    root.innerHTML = resultCat.join('');

    let parentElem = document.getElementById('main');
    parentElem.insertBefore(root, parentElem.firstChild);
}

function fillSubExpensesForUpdate(data, catId) {
    let arr = data;
    let result = [];
    for (j = 0; j < arr.length; j++){
        let item = arr[j];
        let subcatNumber = item['subCategoryNumber'];
        let subcatId = catId + "-subcat-" + subcatNumber + "_0";
        let subcatHtml = `
        <div class="subcategory" id="${subcatId}">
           <div class="subcat-name expense-color">
           ${item['subCategoryName']}
           </div>
           <div class="subcat-value">
              <div class="subcat-value-text">
              <input name="exp-val" type="text" placeholder="0" onblur="updateTotalExpenses()" value="${item['amount']}"/>
              </div>
              <div class="subcat-value-append">
              <span>руб.</span>
              </div>
           </div>
           <div class="subcat-comment">
           <input type="text" placeholder="Комментарий" value="${item['comment']}"/>
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

function fillIncomesForUpdate(data) {
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
        let itemList = arr[i];
        let item = itemList[0];
        let catNumber = item['categoryNumber'];
        let catId = "inc-cat-" + catNumber;
        let catHtml = `
        <div class="inc-cat">
            <div class="category-group" id="$catId">
                 <div class="category-title">
                     <div class="category-marker">
                     *
                     </div>
                     <div class="category-name">
                     <span>${item['categoryName']}</span>
                     </div>
                 </div>
            </div>
            ${fillSubIncomesForUpdate(itemList, catId)}
        </div>
        `;
        resultCat.push(catHtml);
    }
    root.innerHTML = resultCat.join('');

    let parentElem = document.getElementById('main');
    parentElem.insertBefore(root, document.getElementById('right-menu'));
}

function fillSubIncomesForUpdate(data, catId) {
    let arr = data;
    let result = [];
    for (j = 0; j < arr.length; j++){
        let item = arr[j];
        let subcatNumber = item['subCategoryNumber'];
        let subcatId = catId + "-subcat-" + subcatNumber + "_0";
        let subcatHtml = `
        <div class="subcategory" id="${subcatId}">
           <div class="subcat-name income-color">
           ${item['subCategoryName']}
           </div>
           <div class="subcat-value">
              <div class="subcat-value-text">
              <input name="inc-val" type="text" placeholder="0" onblur="updateTotalIncomes()" value="${item['amount']}"/>
              </div>
              <div class="subcat-value-append">
              <span>руб.</span>
              </div>
           </div>
           <div class="subcat-comment">
           <input type="text" placeholder="Комментарий" value="${item['comment']}"/>
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

function updatePlan() {
    let planObj = createShortPlanObject();

    $.ajax({
        url: window.location.href,
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify(planObj),
        dataType: 'json',
        success: function (data, status, settings) {
            showMessage(data);
        }
    });
}


function disableEmptyItems(rootClass) {
    let root = document.getElementsByClassName(rootClass)[0];
    let subcats = root.getElementsByClassName("subcategory");
    for (let i = 0; i < subcats.length; i++) {
        let subcat = subcats[i];
        let subcatValue = subcat.getElementsByClassName("subcat-value-text")[0].children[0].value;
        if (subcatValue === '0') {
            disableSubcategory(subcat.getAttribute('id'));
        }
    }
}