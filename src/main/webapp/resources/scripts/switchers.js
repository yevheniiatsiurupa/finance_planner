function switchAmountMin() {
    document.getElementById('amountMin').disabled = !document.getElementById('amountMinSwitcher').checked;
}
function switchAmountMax() {
    document.getElementById('amountMax').disabled = !document.getElementById('amountMaxSwitcher').checked;
}
function switchCreatedMin() {
    document.getElementById('createdMin').disabled = !document.getElementById('createdMinSwitcher').checked;
}
function switchCreatedMax() {
    document.getElementById('createdMax').disabled = !document.getElementById('createdMaxSwitcher').checked;
}
function switchMonthpicker() {
    document.getElementById('monthpicker').disabled = !document.getElementById('periodSwitcher1').checked;
}
function switchDatepicker() {
    document.getElementById('datepicker1').disabled = !document.getElementById('periodSwitcher2').checked;
    document.getElementById('datepicker2').disabled = !document.getElementById('periodSwitcher2').checked;
}
function switchPeriod(myRadio) {
    var currentValue = myRadio.value;
    if (currentValue === 'month') {
        document.getElementById('monthpicker').disabled = false;
        document.getElementById('datepicker1').disabled = true;
        document.getElementById('datepicker2').disabled = true;
    } else {
        document.getElementById('monthpicker').disabled = true;
        document.getElementById('datepicker1').disabled = false;
        document.getElementById('datepicker2').disabled = false;
    }
}