<#import "fragments/layout.ftl" as layout>
<@layout.page title="Книга">
    <div class="page-head">
        <div>
            <h1>${book.title}</h1>
            <p>${book.author} | ISBN: ${book.isbn} | Категорія: ${book.category.name}</p>
        </div>
        <div class="actions">
            <a class="button" href="/books/${book.id}/edit">Редагувати</a>
            <a class="button button--ghost" href="/books">Назад</a>
        </div>
    </div>

    <section class="card">
        <h2>Примірники</h2>
        <form class="inline-form" method="post" action="/books/${book.id}/copies">
            <input type="text" name="copyCode" placeholder="Новий код примірника">
            <button type="submit">Додати</button>
        </form>
        <table class="table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Код</th>
                <th>Статус</th>
            </tr>
            </thead>
            <tbody>
            <#list book.copies as copy>
                <tr>
                    <td>${copy.id}</td>
                    <td>${copy.copyCode}</td>
                    <td>${copy.status}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </section>

    <section class="card">
        <h2>Хто зарезервував книгу</h2>
        <#if book.reservedByReaders?size == 0>
            <p>Книга ще ніким не зарезервована.</p>
        <#else>
            <ul>
                <#list book.reservedByReaders as reader>
                    <li>${reader.fullName}</li>
                </#list>
            </ul>
        </#if>
    </section>
</@layout.page>
