<#import "/fragments/layout.ftl" as layout>
<@layout.page title="Категорія">
    <div class="page-head">
        <div>
            <h1><#if category.id??>Редагування категорії<#else>Нова категорія</#if></h1>
            <p>Одна форма для створення та редагування категорії.</p>
        </div>
    </div>

    <form class="form" method="post" action="<#if category.id??>/categories/${category.id}/edit<#else>/categories</#if>">
        <label>
            Назва
            <input type="text" name="name" value="${category.name!''}" required>
        </label>
        <button type="submit">Зберегти</button>
    </form>
</@layout.page>
