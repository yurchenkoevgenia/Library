<#import "/fragments/layout.ftl" as layout>
<@layout.page title="Читачі">
    <div class="page-head">
        <div>
            <h1>Читачі</h1>
            <p>Список читачів та їх бібліотечних карток.</p>
        </div>
        <a class="button" href="/readers/new">Додати читача</a>
    </div>

    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>ПІБ</th>
            <th>Email</th>
            <th>Картка</th>
            <th>Дії</th>
        </tr>
        </thead>
        <tbody>
        <#list readers as reader>
            <tr>
                <td>${reader.id}</td>
                <td>${reader.fullName}</td>
                <td>${reader.email}</td>
                <td>${reader.libraryCard.cardNumber}</td>
                <td class="actions">
                    <a href="/readers/${reader.id}">Деталі</a>
                    <a href="/readers/${reader.id}/edit">Редагувати</a>
                    <form method="post" action="/readers/${reader.id}/delete">
                        <button type="submit">Видалити</button>
                    </form>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</@layout.page>
