<template>
  <div class="shell">
    <header class="topbar">
      <div>
        <p class="eyebrow">Spring Boot + Vue 3</p>
        <h1>Library</h1>
      </div>
      <nav class="nav">
        <RouterLink to="/">Головна</RouterLink>
        <RouterLink to="/categories">Категорії</RouterLink>
        <RouterLink to="/books">Книги</RouterLink>
      </nav>
    </header>

    <main class="page">
      <RouterView />
    </main>
  </div>
</template>

