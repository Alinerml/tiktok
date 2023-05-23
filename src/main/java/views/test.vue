<template>
  <div>
    <button @click="makeRequest">发起请求</button>
    <p v-if="response">响应：{{ response }}</p >
    <p v-if="error">错误：{{ error }}</p >
    <div>hihi</div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      response: null,
      error: null
    };
  },
  methods: {
    makeRequest() {
      axios.get('http://localhost:8080/user/login/')
          .then(response => {
            this.response = response.data;
            this.error = null;
          })
          .catch(error => {
            this.response = null;
            this.error = error.message;
          });
    }
  }
};
</script>