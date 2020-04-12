function moveUpCategory(catId) {
    let catElem = document.getElementById(catId);
    let cardElem = catElem.closest(".card");
    if(cardElem.previousElementSibling) {
        cardElem.parentNode.insertBefore(cardElem, cardElem.previousElementSibling);
    }
}
function moveDownCategory(catId) {
    let catElem = document.getElementById(catId);
    let cardElem = catElem.closest(".card");
    if(cardElem.nextElementSibling) {
        cardElem.parentNode.insertBefore(cardElem.nextElementSibling, cardElem);
    }
}
function deleteCategory(catId) {
    let catElem = document.getElementById(catId);
    let cardElem = catElem.closest(".card");
    cardElem.parentNode.removeChild(cardElem);
}
function moveUpSubcategory(subcatId) {
    let subcatElem = document.getElementById(subcatId);
    let listElem = subcatElem.closest(".subcategory");
    if(listElem.previousElementSibling) {
        listElem.parentNode.insertBefore(listElem, listElem.previousElementSibling);
    }
}
function moveDownSubcategory(subcatId) {
    let subcatElem = document.getElementById(subcatId);
    let listElem = subcatElem.closest(".subcategory");
    if(listElem.nextElementSibling) {
        listElem.parentNode.insertBefore(listElem.nextElementSibling, listElem);
    }
}
function deleteSubcategory(subcatId) {
    let subcatElem = document.getElementById(subcatId);
    let listElem = subcatElem.closest(".subcategory");
    let parent = listElem.parentNode;
    if (parent.children.length > 1) {
        parent.removeChild(listElem);
    }
}

function addSubcategory(catId) {
    let root = document.getElementById(catId).closest(".card-body").getElementsByClassName('subcategories')[0];
    let subcats = root.getElementsByClassName("subcategory");
    let clone = subcats[0].cloneNode(true);
    let newId = getNewId(subcats);

    clone.getElementsByTagName('input')[0].setAttribute('id', newId);
    clone.getElementsByTagName('input')[0].setAttribute('value', '');
    clone.getElementsByClassName('button-down')[0]
        .setAttribute('onclick', `moveDownSubcategory('${newId}')`);
    clone.getElementsByClassName('button-up')[0]
        .setAttribute('onclick', `moveUpSubcategory('${newId}')`);
    clone.getElementsByClassName('button-delete')[0]
        .setAttribute('onclick', `deleteSubcategory('${newId}')`);

    root.appendChild(clone);
}

function getNewId(items) {
    let arrIds = [];
    let idStart = '';
    for (let i = 0; i < items.length; i++) {
        let item = items[i];
        let itemId = item.getElementsByTagName('input')[0].getAttribute('id');
        let idParts = itemId.split('-');
        let itemBareId = idParts[idParts.length - 1];
        if (i === 0) {
            idParts.pop();
            idStart = idParts.join("-");
        }
        arrIds.push(Number(itemBareId));
    }

    let newIdSuffix = Math.max(...arrIds) + 1;
    return idStart + "-" + newIdSuffix;
}

function addExpenseCategory() {
    let root = document.getElementById("expenses-box").getElementsByClassName("card-box")[0];
    let newId = getNewId(root.getElementsByClassName('category'));
    let colorClass = "expense-color";
    addCategory(root, newId, colorClass);
}

function addIncomeCategory() {
    let root = document.getElementById("incomes-box").getElementsByClassName("card-box")[0];
    let newId = getNewId(root.getElementsByClassName('category'));
    let colorClass = "income-color";
    addCategory(root, newId, colorClass);
}

function addCategory(root, newId, colorClass) {
    let newCard = document.createElement('div');
    newCard.className = "card bg-light mb-3";
    newCard.innerHTML = `
    <div class="card-body">
        <div class="category">
            <div class="card-box-col-lg border-right">
                <div class="category-box">
                    <div class="category-marker">●</div>
                    <div class="category-input">
                        <input type="text" class="form-control ${colorClass}" id="${newId}"/>
                    </div>
                </div>
            </div>
            <div class="card-box-col-sm">
                <div class="button-box">
                    <button class="button-down" onclick="moveDownCategory('${newId}')"></button>
                    <button class="button-up" onclick="moveUpCategory('${newId}')"></button>
                    <button class="button-delete" onclick="deleteCategory('${newId}')"></button>
                </div>
            </div>
        </div>
        <div class="subcategories">
            <div class="subcategory">
                <div class="card-box-col-lg border-right">
                    <div class="subcategory-input">
                        <input type="text" class="form-control" id="${newId}-subcat-1"/>
                    </div>
                </div>
                <div class="card-box-col-sm">
                    <div class="button-box">
                        <button class="button-down" onclick="moveDownSubcategory('${newId}-subcat-1')"></button>
                        <button class="button-up" onclick="moveUpSubcategory('${newId}-subcat-1')"></button>
                        <button class="button-delete" onclick="deleteSubcategory('${newId}-subcat-1')"></button>
                    </div>
                </div>
            </div>
        </div>
        <div class="button-add-subcat">
            <button class="btn btn-primary" onclick="addSubcategory('${newId}')">+</button>
        </div>
    </div>
    `;
    root.appendChild(newCard);
}


