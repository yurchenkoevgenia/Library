<#import "fragments/layout.ftl" as layout>
<@layout.page title="Категорії">
    <div class="page-head">
        <div>
            <h1>Категорії</h1>
            <p>Керуйте категоріями бібліотеки.</p>
        </div>
        <a class="button" href="/categories/new">Додати категорію</a>
    </div>
    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Назва</th>
            <th>Книг</th>
            <th>Дії</th>
        </tr>
        </thead>
        <tbody>
        <#list categories as category>
            <tr>
                <td>${category.id}</td>
                <td>${category.name}</td>
                <td>${category.books?size}</td>
                <td class="actions">
                    <a href="/categories/${category.id}/edit">Редагувати</a>
                    <form method="post" action="/categories/${category.id}/delete">
                        <button type="submit">Видалити</button>
                    </form>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</@layout.page>
