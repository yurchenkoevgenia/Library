<#import "/fragments/layout.ftl" as layout>
<@layout.page title="Резервування">
    <div class="page-head">
        <div>
            <h1>Резервування книг</h1>
            <p>Сесія зберігає вибраного читача, після чого можна резервувати та скасовувати книги.</p>
        </div>
    </div>

    <section class="card">
        <form class="inline-form" method="post" action="/reservations/select-reader">
            <label>
                Вибери читача
                <select name="readerId" required>
                    <#list readers as reader>
                        <option value="${reader.id}" <#if selectedReader?? && selectedReader.id == reader.id>selected</#if>>${reader.fullName}</option>
                    </#list>
                </select>
            </label>
            <button type="submit">Вибрати</button>
        </form>
        <#if selectedReader??>
            <p>Поточний читач: <strong>${selectedReader.fullName}</strong></p>
        <#else>
            <p>Читача ще не вибрано.</p>
        </#if>
    </section>

    <table class="table">
        <thead>
        <tr>
            <th>Назва</th>
            <th>Автор</th>
            <th>Категорія</th>
            <th>Дії</th>
        </tr>
        </thead>
        <tbody>
        <#list books as book>
            <tr>
                <td><a href="/books/${book.id}">${book.title}</a></td>
                <td>${book.author}</td>
                <td>${book.category.name}</td>
                <td class="actions">
                    <#if selectedReader?? && reservedBookIds?seq_contains(book.id)>
                        <form method="post" action="/reservations/${book.id}/cancel">
                            <button type="submit">Скасувати</button>
                        </form>
                    <#else>
                        <form method="post" action="/reservations/${book.id}/reserve">
                            <button type="submit">Резервувати</button>
                        </form>
                    </#if>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</@layout.page>
