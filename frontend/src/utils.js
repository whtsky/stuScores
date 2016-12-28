import axios from 'axios';

export const API = axios.create({
  baseURL: '/api',
  timeout: 1000,
  transformRequest(data, headers) {
    headers['Content-Type'] = 'application/x-www-form-urlencoded'
    const str = [];
    for(const p in data)
      if (data.hasOwnProperty(p) && data[p]) {
        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(data[p]))
      }
    return str.join("&")
  }
})
