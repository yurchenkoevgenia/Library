<template>
  <section class="panel">
    <div class="panel__head">
      <div>
        <p class="eyebrow">CRUD</p>
        <h2>Книги</h2>
      </div>
      <div class="search">
        <input v-model="query" type="search" placeholder="Пошук за назвою або автором" @input="loadBooks" />
      </div>
    </div>

    <div class="grid grid--two">
      <form class="card form" @submit.prevent="save">
        <label>
          Назва
          <input v-model="form.title" type="text" placeholder="The Hobbit" />
        </label>
        <label>
          Автор
          <input v-model="form.author" type="text" placeholder="J. R. R. Tolkien" />
        </label>
        <label>
          ISBN
          <input v-model="form.isbn" type="text" placeholder="9780547928227" />
        </label>
        <label>
          Категорія
          <select v-model="form.categoryId">
            <option value="" disabled>Оберіть категорію</option>
            <option v-for="category in categories" :key="category.id" :value="category.id">
              {{ category.name }}
            </option>
          </select>
        </label>
        <div class="actions">
          <button class="button" type="submit">{{ form.id ? 'Оновити' : 'Створити' }}</button>
          <button class="button button--ghost" type="button" @click="resetForm">Скинути</button>
        </div>
        <p v-if="message" class="message">{{ message }}</p>
      </form>

      <div class="card table-card">
        <table>
          <thead>
            <tr>
              <th>Назва</th>
              <th>Автор</th>
              <th>Категорія</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="book in books" :key="book.id">
              <td>{{ book.title }}</td>
              <td>{{ book.author }}</td>
              <td>{{ book.categoryName }}</td>
              <td class="row-actions">
                <button class="link" @click="edit(book)">Редагувати</button>
                <button class="link link--danger" @click="remove(book.id)">Видалити</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </section>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { createBook, deleteBook, getBooks, getCategories, updateBook } from '../api/libraryApi'

const books = ref([])
const categories = ref([])
const query = ref('')
const message = ref('')
const form = reactive({ id: null, title: '', author: '', isbn: '', categoryId: '' })

async function loadCategories() {
  categories.value = await getCategories()
  if (!form.categoryId && categories.value.length > 0) {
    form.categoryId = categories.value[0].id
  }
}

async function loadBooks() {
  books.value = await getBooks(query.value)
}

function resetForm() {
  form.id = null
  form.title = ''
  form.author = ''
  form.isbn = ''
  form.categoryId = ''
  message.value = ''
}

function edit(book) {
  form.id = book.id
  form.title = book.title
  form.author = book.author
  form.isbn = book.isbn
  form.categoryId = book.categoryId
  message.value = ''
}

async function save() {
  try {
    const payload = {
      title: form.title,
      author: form.author,
      isbn: form.isbn,
      categoryId: Number(form.categoryId),
    }

    if (form.id) {
      await updateBook(form.id, payload)
      message.value = 'Книгу оновлено.'
    } else {
      await createBook(payload)
      message.value = 'Книгу створено.'
    }

    await loadBooks()
    resetForm()
  } catch (error) {
    message.value = error?.response?.data?.message || error.message || 'Помилка під час збереження.'
  }
}

async function remove(id) {
  try {
    await deleteBook(id)
    await loadBooks()
  } catch (error) {
    message.value = error?.response?.data?.message || error.message || 'Помилка під час видалення.'
  }
}

onMounted(async () => {
  await loadCategories()
  await loadBooks()
})
</script>
