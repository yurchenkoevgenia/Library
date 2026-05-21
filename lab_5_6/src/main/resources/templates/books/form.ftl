<#import "/fragments/layout.ftl" as layout>
<@layout.page title="Книга">
    <div class="page-head">
        <div>
            <h1><#if book.id??>Редагування книги<#else>Нова книга</#if></h1>
            <p>Заповни дані книги та вкажи категорію.</p>
        </div>
    </div>

    <form class="form" method="post" action="<#if book.id??>/books/${book.id}/edit<#else>/books</#if>">
        <label>
            Назва
            <input type="text" name="title" value="${book.title!''}" required>
        </label>
        <label>
            Автор
            <input type="text" name="author" value="${book.author!''}" required>
        </label>
        <label>
            ISBN
            <input type="text" name="isbn" value="${book.isbn!''}" required>
        </label>
        <label>
            Категорія
            <select name="categoryId" required>
                <#list categories as category>
                    <option value="${category.id}" <#if selectedCategoryId?? && selectedCategoryId == category.id>selected</#if>>${category.name}</option>
                </#list>
            </select>
        </label>
        <label>
            Код першого примірника
            <input type="text" name="copyCode" value="${copyCode!''}">
        </label>
        <button type="submit">Зберегти</button>
    </form>
</@layout.page>
