<#macro page title>
<!DOCTYPE html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title} - Library</title>
    <link rel="stylesheet" href="/css/app.css">
</head>
<body>
<header class="topbar">
    <div class="topbar__brand">Library</div>
    <nav class="topbar__nav">
        <a href="/">Головна</a>
        <a href="/categories">Категорії</a>
        <a href="/books">Книги</a>
        <a href="/readers">Читачі</a>
        <a href="/reservations">Резервування</a>
    </nav>
</header>
<main class="container">
    <#if message??>
        <div class="alert alert-success">${message}</div>
    </#if>
    <#if error??>
        <div class="alert alert-error">${error}</div>
    </#if>
    <#nested>
</main>
</body>
</html>
</#macro>
