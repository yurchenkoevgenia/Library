<#import "fragments/layout.ftl" as layout>
<@layout.page title="Головна">
    <section class="hero">
        <h1>Веб-додаток Library</h1>
        <p>Лабораторна робота 7 демонструє Spring Boot, MVC, FreeMarker, JPA, MySQL і сесії.</p>
        <div class="stats">
            <div class="stat-card">
                <span class="stat-card__value">${categoryCount}</span>
                <span class="stat-card__label">Категорій</span>
            </div>
            <div class="stat-card">
                <span class="stat-card__value">${bookCount}</span>
                <span class="stat-card__label">Книг</span>
            </div>
            <div class="stat-card">
                <span class="stat-card__value">${readerCount}</span>
                <span class="stat-card__label">Читачів</span>
            </div>
            <div class="stat-card">
                <span class="stat-card__value">${reservedBookCount}</span>
                <span class="stat-card__label">Резервувань</span>
            </div>
        </div>
    </section>
</@layout.page>
