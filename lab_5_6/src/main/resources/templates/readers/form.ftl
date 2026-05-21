<#import "/fragments/layout.ftl" as layout>
<@layout.page title="Читач">
    <div class="page-head">
        <div>
            <h1><#if reader.id??>Редагування читача<#else>Новий читач</#if></h1>
            <p>Форма для створення або редагування читача.</p>
        </div>
    </div>

    <form class="form" method="post" action="<#if reader.id??>/readers/${reader.id}/edit<#else>/readers</#if>">
        <label>
            ПІБ
            <input type="text" name="fullName" value="${reader.fullName!''}" required>
        </label>
        <label>
            Email
            <input type="email" name="email" value="${reader.email!''}" required>
        </label>
        <label>
            Телефон
            <input type="text" name="phoneNumber" value="${reader.phoneNumber!''}">
        </label>
        <label>
            Номер картки
            <input type="text" name="cardNumber" value="${cardNumber!''}">
        </label>
        <button type="submit">Зберегти</button>
    </form>
</@layout.page>
