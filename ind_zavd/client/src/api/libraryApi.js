import axios from 'axios'

const http = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'application/json',
  },
})

export const getCategories = () => http.get('/categories').then((response) => response.data)
export const createCategory = (payload) => http.post('/categories', payload).then((response) => response.data)
export const updateCategory = (id, payload) => http.put(`/categories/${id}`, payload).then((response) => response.data)
export const deleteCategory = (id) => http.delete(`/categories/${id}`)

export const getBooks = (query = '') => {
  const params = query ? { q: query } : {}
  return http.get('/books', { params }).then((response) => response.data)
}

export const createBook = (payload) => http.post('/books', payload).then((response) => response.data)
export const updateBook = (id, payload) => http.put(`/books/${id}`, payload).then((response) => response.data)
export const deleteBook = (id) => http.delete(`/books/${id}`)

