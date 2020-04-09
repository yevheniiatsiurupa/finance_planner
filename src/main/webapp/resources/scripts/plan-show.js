function loadPlan() {
    let urlParts = window.location.href.split("?")[0].split("/");
    let id = urlParts[urlParts.length - 1];
    $.ajax({
        url: "./show/expenses",
        type: 'post',
        contentType: 'text/plain',
        data: id,
        dataType: 'json',
        success: function (data, status, settings) {
            fillExpensesFromDb(data);
        }
    });
    $.ajax({
        url: "./show/incomes",
        type: 'post',
        contentType: 'text/plain',
        data: id,
        dataType: 'json',
        success: function (data, status, settings) {
            fillIncomesFromDb(data);
        }
    });
}

function fillExpensesFromDb(data) {
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
            ${fillSubExpensesFromDb(itemList, catId)}
        </div>
        `;
        resultCat.push(catHtml);
    }
    root.innerHTML = resultCat.join('');

    let parentElem = document.getElementById('main');
    parentElem.insertBefore(root, parentElem.firstChild);
}

function fillSubExpensesFromDb(data, catId) {
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
              <span>${item['amount']}</span>
              </div>
              <div class="subcat-value-append">
              <span>руб.</span>
              </div>
           </div>
        </div>
        `;
        result.push(subcatHtml);
    }
    return result.join('');
}

function fillIncomesFromDb(data) {
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
            ${fillSubIncomesFromDb(itemList, catId)}
        </div>
        `;
        resultCat.push(catHtml);
    }
    root.innerHTML = resultCat.join('');

    let parentElem = document.getElementById('main');
    parentElem.insertBefore(root, document.getElementById('right-menu'));
}

function fillSubIncomesFromDb(data, catId) {
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
              <span>${item['amount']}</span>
              </div>
              <div class="subcat-value-append">
              <span>руб.</span>
              </div>
           </div>
        </div>
        `;
        result.push(subcatHtml);
    }
    return result.join('');
}