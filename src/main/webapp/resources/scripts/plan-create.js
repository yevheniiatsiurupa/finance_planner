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

function savePlan() {
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
