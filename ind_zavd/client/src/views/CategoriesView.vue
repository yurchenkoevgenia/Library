<template>
  <section class="panel">
    <div class="panel__head">
      <div>
        <p class="eyebrow">CRUD</p>
        <h2>Категорії</h2>
      </div>
      <button class="button button--ghost" @click="resetForm">Нова категорія</button>
    </div>

    <div class="grid grid--two">
      <form class="card form" @submit.prevent="save">
        <label>
          Назва
          <input v-model="form.name" type="text" placeholder="Наприклад: Fiction" />
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
              <th>Книг</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="category in categories" :key="category.id">
              <td>{{ category.name }}</td>
              <td>{{ category.bookCount }}</td>
              <td class="row-actions">
                <button class="link" @click="edit(category)">Редагувати</button>
                <button class="link link--danger" @click="remove(category.id)">Видалити</button>
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
import { createCategory, deleteCategory, getCategories, updateCategory } from '../api/libraryApi'

const categories = ref([])
const message = ref('')
const form = reactive({ id: null, name: '' })

async function load() {
  categories.value = await getCategories()
}

function resetForm() {
  form.id = null
  form.name = ''
  message.value = ''
}

function edit(category) {
  form.id = category.id
  form.name = category.name
  message.value = ''
}

async function save() {
  try {
    const payload = { name: form.name }
    if (form.id) {
      await updateCategory(form.id, payload)
      message.value = 'Категорію оновлено.'
    } else {
      await createCategory(payload)
      message.value = 'Категорію створено.'
    }
    await load()
    resetForm()
  } catch (error) {
    message.value = error?.response?.data?.message || error.message || 'Помилка під час збереження.'
  }
}

async function remove(id) {
  try {
    await deleteCategory(id)
    await load()
  } catch (error) {
    message.value = error?.response?.data?.message || error.message || 'Помилка під час видалення.'
  }
}

onMounted(load)
</script>
