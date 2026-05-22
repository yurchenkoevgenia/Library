<template>
  <section class="hero">
    <div class="hero__copy">
      <p class="eyebrow">Індивідуальне завдання</p>
      <h2>Клієнт-серверний веб-додаток для бібліотеки</h2>
      <p>
        Фронтенд працює на Vue 3, а дані отримує з REST API Spring Boot. Тут можна
        створювати, редагувати та видаляти категорії й книги.
      </p>
    </div>

    <div class="grid">
      <article class="card stat">
        <span>Категорії</span>
        <strong>{{ categoryCount }}</strong>
      </article>
      <article class="card stat">
        <span>Книги</span>
        <strong>{{ bookCount }}</strong>
      </article>
    </div>
  </section>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { getBooks, getCategories } from '../api/libraryApi'

const categoryCount = ref('...')
const bookCount = ref('...')

onMounted(async () => {
  const [categories, books] = await Promise.all([getCategories(), getBooks()])
  categoryCount.value = categories.length
  bookCount.value = books.length
})
</script>

