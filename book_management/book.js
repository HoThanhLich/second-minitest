
getAllBooks();

function getAllBooks() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/books",
        success: function (array) {
            let books = array.content;
            let content = '';
            for (let i = 0; i < books.length; i++) {
                content = content + `<tr>
            <td>${i + 1}</td>
            <td>${books[i].name}</td>
            <td>${books[i].price}</td>
            <td>${books[i].author}</td>
            <td><img src="http://localhost:8080/image/${books[i].image}"></td>
            <td>${books[i].category.name}</td>
            <td><button onclick="showEditForm(${books[i].id})">Edit</button></td>
            <td><button  onclick="showDeleteForm(${books[i].id})">Delete</button></td>
        </tr>`
            }
            $('#book-list-conten').html(content);
        }
    })
}

function showDeleteForm(id) {
    $.ajax({

    })
}

function showEditForm(id) {
    $.ajax({
        type : 'GET',
        url : 'http://localhost:8080/books/${id}',
        success : function (data) {
            let oldImage = `<img src="http://localhost:8080/image/${data.image}" width="100" height="100">`;
            $('#oldName').val(data.name);
            $('#oldPrice').val(data.price);
            $('#author').val(data.author);
            $('#category').val(data.category.name);
            $('#showImg').html(oldImage);
        }
    })
}

function showCreateForm() {
    let drawAdd = `<table id="add-new-book-form">
            <tr><h3>Create new book</h3></tr>
            <tr >
                <td>Name:</td>
                <td><input type="text" id="newName"></td>
            </tr>
            <tr>
                <td>Price:</td>
                <td><input type="text" id="newPrice"></td>
            </tr>
            <tr>
                <td>Author:</td>
                <td><input type="text" id="newAuthor"></td>
            </tr>
            <tr>
                <td>Image:</td>
                <td><input type="file" id="newImage"></td>
            </tr>
            <tr>
                <td>Category:</td>
                <td>
                <select id="newCategory">
                    <option value="1">truyen tranh</option>
                    <option value="2">truyen ngan</option>
                    <option value="3">tieu thuyet</option>
                </select>
                </td>
            </tr>
            <tr>
            <td><button id="add" style="border-radius: 8px; width: 150%" onclick="addNewBook()">Add</button></td>
            </tr>
       </table>`;
    $('#create-form').html(drawAdd);
}

function addNewBook() {
    let newName = $('#newName').val();
    let newPrice = $('#newPrice').val();
    let newAuthor = $('#newAuthor').val();
    let newImage = $('#newImage');
    let newCategory = $('#newCategory').val();
    let newBook = new FormData();
    newBook.append('name', newName);
    newBook.append('price', newPrice);
    newBook.append('author', newAuthor);
    newBook.append('category', newCategory);
    newBook.append('image', newImage.prop('files')[0]);
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/books',
        data: newBook,
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        success: function () {
            getAllBooks();
            alert('Thanh Cong!')
        },
        error: function () {
            alert('That Bai!')
        }
    })
}

