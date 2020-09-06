import Vue from 'vue'
import axios from 'axios'
import VueAxios from 'vue-axios'

const AUTH_CONFIG = {
  headers: {
    Authorization: `Bearer ${localStorage.getItem('token')}` || ''
  }
};

const ApiService = {
  init() {
    Vue.use(VueAxios, axios)
  },
  get(uri) {
    return Vue.axios.get(`${uri}`, AUTH_CONFIG)
  },
  login(uri, config) {
    return Vue.axios.post(`${uri}`, {}, config)
  },
  post(uri, params) {
    return Vue.axios.post(`${uri}`, params, AUTH_CONFIG)
  },
  update(uri, params) {
    return Vue.axios.put(uri, params, AUTH_CONFIG)
  },
  delete(uri) {
    return Vue.axios.delete(uri, AUTH_CONFIG)
  }
}

ApiService.init()

export default ApiService
