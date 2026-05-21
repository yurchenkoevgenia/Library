<#import "fragments/layout.ftl" as layout>
<@layout.page title="Книги">
    <div class="page-head">
        <div>
            <h1>Книги</h1>
            <p>Пошук, перегляд і додавання книг каталогу.</p>
        </div>
        <a class="button" href="/books/new">Додати книгу</a>
    </div>

    <form class="search" method="get" action="/books">
        <input type="text" name="q" placeholder="Пошук за назвою або автором" value="${searchQuery!''}">
        <button type="submit">Знайти</button>
    </form>

    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Назва</th>
            <th>Автор</th>
            <th>ISBN</th>
            <th>Категорія</th>
            <th>Дії</th>
        </tr>
        </thead>
        <tbody>
        <#list books as book>
            <tr>
                <td>${book.id}</td>
                <td>${book.title}</td>
                <td>${book.author}</td>
                <td>${book.isbn}</td>
                <td>${book.category.name}</td>
                <td class="actions">
                    <a href="/books/${book.id}">Деталі</a>
                    <a href="/books/${book.id}/edit">Редагувати</a>
                    <form method="post" action="/books/${book.id}/delete">
                        <button type="submit">Видалити</button>
                    </form>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</@layout.page>
