function loadComparison() {
    let urlParts = window.location.href.split("?")[0].split("/");
    let id = urlParts[urlParts.length - 1];
    $.ajax({
        url: ".",
        type: 'post',
        contentType: 'text/plain',
        data: id,
        dataType: 'json',
        success: function (data, status, settings) {
            fillTotalExpenses(data);
            fillTotalIncomes(data);
            fillTotalOverall(data);
        }
    });
}

function fillTotalExpenses(data) {
    let categories = data['expensesCompare'];
    let rootTable = document.getElementById('table-expenses').getElementsByTagName('tbody')[0];
    for (let i = 0; i < categories.length; i++) {
        let category = categories[i];
        addCategoryRow(category, rootTable);

        let comparisons = category['comparisons'];
        for (let j = 0; j < comparisons.length; j++) {
            let comparison = comparisons[j];
            let difference = getDifferenceString(comparison['plannedValue'],comparison['actualValue']);

            addComparisonRow(comparison, rootTable, difference);
        }
    }
    let totalPlanned = data['expensesPlannedSum'];
    let totalActual = data['expensesActualSum'];
    let difference = getDifferenceString(totalPlanned, totalActual);

    let tableData = {
        rowName: "Итог",
        totalPlanned: totalPlanned,
        totalActual: totalActual,
        difference: difference,
        needTopBorder: true
    };

    addTotalRow(rootTable, tableData);
}

function fillTotalIncomes(data) {
    let categories = data['incomesCompare'];
    let rootTable = document.getElementById('table-incomes').getElementsByTagName('tbody')[0];
    for (let i = 0; i < categories.length; i++) {
        let category = categories[i];
        addCategoryRow(category, rootTable);

        let comparisons = category['comparisons'];
        for (let j = 0; j < comparisons.length; j++) {
            let comparison = comparisons[j];
            let difference = getDifferenceString(comparison['actualValue'], comparison['plannedValue']);

            addComparisonRow(comparison, rootTable, difference);
        }
    }
    let totalPlanned = data['incomesPlannedSum'];
    let totalActual = data['incomesActualSum'];
    let difference = getDifferenceString(totalActual, totalPlanned);

    let tableData = {
        rowName: "Итог",
        totalPlanned: totalPlanned,
        totalActual: totalActual,
        difference: difference,
        needTopBorder: true
    };

    addTotalRow(rootTable, tableData);
}

function fillTotalOverall(data) {
    let rootTable = document.getElementById('table-overall').getElementsByTagName('tbody')[0];

    let totalPlannedExp = data['expensesPlannedSum'];
    let totalActualExp = data['expensesActualSum'];
    let differenceExp = getDifferenceString(totalPlannedExp, totalActualExp);
    let tableDataExp = {
        rowName: "Итого доходы",
        totalPlanned: totalPlannedExp,
        totalActual: totalActualExp,
        difference: differenceExp,
        needTopBorder: false
    };
    addTotalRow(rootTable, tableDataExp);

    let totalPlannedInc = data['incomesPlannedSum'];
    let totalActualInc = data['incomesActualSum'];
    let differenceInc = getDifferenceString(totalActualInc, totalPlannedInc);
    let tableDataInc = {
        rowName: "Итого расходы",
        totalPlanned: totalPlannedInc,
        totalActual: totalActualInc,
        difference: differenceInc,
        needTopBorder: false
    };
    addTotalRow(rootTable, tableDataInc);

    let profitPlanned = getDifferenceString(totalPlannedInc, totalPlannedExp);
    let profitActual = getDifferenceString(totalActualInc, totalActualExp);

    let profitRow = document.createElement('tr');
    profitRow.className = 'border-top';
    profitRow.innerHTML = `
            <td class="border-right table-sm-left">Экономия</td>
            <td class="${getDifferenceClass(profitPlanned)}">${profitPlanned}</td>
            <td class="${getDifferenceClass(profitActual)}">${profitActual}</td>
            <td class="border-left"></td>
    `;
    rootTable.appendChild(profitRow);
}

function addCategoryRow(category, rootTable) {
    let catRow = document.createElement('tr');

    catRow.innerHTML = `
        <td class="border-right">
            <div class="category">
                <div class="category-marker">*</div>
                <div class="category-name">
                    <span>${category['categoryName']}</span>
                </div>
            </div>
        </td>
        <td></td>
        <td></td>
        <td class="border-left"></td>
        `;
    rootTable.appendChild(catRow);
}


function addComparisonRow(comparison, rootTable, difference) {
    let subcatRow = document.createElement('tr');
    subcatRow.innerHTML = `
            <td class="border-right">
                <div class="subcategory">
                    <span>${comparison['subcategoryName']}</span>
                </div>
            </td>
            <td>${comparison['plannedValue']}</td>
            <td>${comparison['actualValue']}</td>
            <td class="${getDifferenceClass(difference)} border-left"><b>${difference}</b></td>
            `;
    rootTable.appendChild(subcatRow);
}


function addTotalRow(rootTable, tableData) {
    let totalRow = document.createElement('tr');
    if (tableData.needTopBorder) {
        totalRow.className = 'border-top';
    }
    totalRow.innerHTML = `
            <td class="border-right table-sm-left">${tableData.rowName}</td>
            <td>${tableData.totalPlanned}</td>
            <td>${tableData.totalActual}</td>
            <td class="${getDifferenceClass(tableData.difference)} border-left"><b>${tableData.difference}</b></td>
    `;
    rootTable.appendChild(totalRow);
}

function getDifferenceString(first, second) {
    let difference = first - second;
    if (difference > 0) {
        return '+' + difference;
    } else {
        return String(difference);
    }
}

function getDifferenceClass(difference) {
    if (difference.startsWith('+')) {
        return 'text-success';
    }
    if (difference.startsWith('-')) {
        return 'text-danger';
    }
    return '';
}