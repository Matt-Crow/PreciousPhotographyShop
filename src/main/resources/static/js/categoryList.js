/*
 * This file is used by the postPhotographFormPage
 */

function registerKeyListener(){
    let ele = document.getElementById("newCategory");
    ele.onkeypress = handleKeyPress;
}

function handleKeyPress(event){
    // keyCode 13 is enter key
    if(event.keyCode === 13){
        let ele = document.getElementById("newCategory");
        addToCategoryList(ele.value);
        ele.value = "";
        
        // prevent submitting the form on enter
        event.preventDefault();
        return false;
    }
}

function addToCategoryList(newCategory){
    let categoryList = document.getElementById("categories");
    let newCategoryElement = document.createElement("tr");
    let td = document.createElement("td");
    td.innerHTML = newCategory;
    newCategoryElement.appendChild(td);
    categoryList.insertBefore(newCategoryElement, document.getElementById("newCategory"));
}

export {
    registerKeyListener
};