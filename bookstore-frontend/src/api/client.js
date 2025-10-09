import axios from "axios";

const STATIC_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYW1pbCIsImlhdCI6MTc2MDAxODkzMywiZXhwIjoxNzYwMDIyNTMzfQ.Pn90K-DPNwStKmC-Oj1Q8sfD5kE1qFnMNwvZOBfXys0";
const client = axios.create({
  baseURL: "http://localhost:8080/api",
  headers: {
    "Content-Type": "application/json",
    "Authorization": `Bearer ${STATIC_TOKEN}`
  },
});

export default client;
