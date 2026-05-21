<#import "fragments/layout.ftl" as layout>
<@layout.page title="Читач">
    <div class="page-head">
        <div>
            <h1>${reader.fullName}</h1>
            <p>${reader.email} | ${reader.phoneNumber!''}</p>
        </div>
        <div class="actions">
            <a class="button" href="/readers/${reader.id}/edit">Редагувати</a>
            <a class="button button--ghost" href="/readers">Назад</a>
        </div>
    </div>

    <section class="card">
        <h2>Бібліотечна картка</h2>
        <p>Номер: ${reader.libraryCard.cardNumber}</p>
        <p>Дата видачі: ${reader.libraryCard.issueDate}</p>
        <p>Дійсна до: ${reader.libraryCard.expireDate!''}</p>
    </section>

    <section class="card">
        <h2>Зарезервовані книги</h2>
        <#if reader.reservedBooks?size == 0>
            <p>Читач ще не має резервувань.</p>
        <#else>
            <ul>
                <#list reader.reservedBooks as book>
                    <li><a href="/books/${book.id}">${book.title}</a></li>
                </#list>
            </ul>
        </#if>
    </section>
</@layout.page>
