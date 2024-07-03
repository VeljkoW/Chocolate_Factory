<template>
  <div>
    <div class="top-section">
      <h1>Registered Users</h1>
      <button class="back-button" @click="goBack">Go back</button>
    </div>
    <div class="filter-section">
      <div class="filter-controls">
        <select v-model="selectedRole" class="filter-select">
          <option value="">All Roles</option>
          <option value="Customer">Customer</option>
          <option value="Employee">Employee</option>
          <option value="Manager">Manager</option>
          <option value="Administrator">Administrator</option>
        </select>
        <button class="filter-button" @click="filterUsers">Filter</button>
        <button class="reset-button" @click="resetFilter">Reset</button>
      </div>
    </div>
    <div class="search-section">
      <div class="search-field">
        <label for="usernameSearch">Username</label>
        <input type="text" v-model="searchUsername" id="usernameSearch" placeholder="Search by username" class="search-input"/>
      </div>
      <div class="search-field">
        <label for="nameSearch">Name</label>
        <input type="text" v-model="searchName" id="nameSearch" placeholder="Search by name" class="search-input"/>
      </div>
      <div class="search-field">
        <label for="surnameSearch">Surname</label>
        <input type="text" v-model="searchSurname" id="surnameSearch" placeholder="Search by surname" class="search-input"/>
      </div>
      <button class="search-button" @click="searchUsers">Search</button>
    </div>
    <div class="table-container-users">
      <table>
        <thead>
          <tr>
            <th @click="sortUsers('username')">Username</th>
            <th @click="sortUsers('name')">Name</th>
            <th @click="sortUsers('surname')">Surname</th>
            <th @click="sortUsers('gender')">Gender</th>
            <th @click="sortUsers('dateOfBirth')">Date of Birth</th>
            <th @click="sortUsers('uloga')">Role</th>
            <th @click="sortUsers('points')">Points</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="user in filteredUsers" :key="user.username">
            <td>{{ user.username }}</td>
            <td>{{ user.name }}</td>
            <td>{{ user.surname }}</td>
            <td>{{ user.gender }}</td>
            <td>{{ user.dateOfBirth }}</td>
            <td>{{ user.uloga }}</td>
            <td>{{ user.points}}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      users: [],
      selectedRole: '',
      searchUsername: '',
      searchName: '',
      searchSurname: '',
      filteredUsers: [],
      sortKey: '',
      sortOrder: 1
    };
  },
  created() {
    this.fetchUsers();
  },
  methods: {
    async fetchUsers() {
      try {
        const response = await axios.get('http://localhost:8080/WebShopAppREST/rest/user/all');
        this.users = response.data;
        this.filteredUsers = this.users;
      } catch (error) {
        console.error('Error fetching users:', error);
        alert('Failed to fetch users. Please try again later.');
      }
    },
    filterUsers() {
      this.filteredUsers = this.users.filter(user => {
        const matchesRole = this.selectedRole === '' || user.uloga === this.selectedRole;
        const matchesUsername = this.searchUsername === '' || user.username.toLowerCase().includes(this.searchUsername.toLowerCase());
        const matchesName = this.searchName === '' || user.name.toLowerCase().includes(this.searchName.toLowerCase());
        const matchesSurname = this.searchSurname === '' || user.surname.toLowerCase().includes(this.searchSurname.toLowerCase());
        return matchesRole && matchesUsername && matchesName && matchesSurname;
      });
    },
    resetFilter() {
      this.selectedRole = '';
      this.searchUsername = '';
      this.searchName = '';
      this.searchSurname = '';
      this.filteredUsers = this.users;
    },
    searchUsers() {
      this.filterUsers();
    },
    goBack() {
      this.$router.push('/');
    },
    sortUsers(key) {
      this.sortOrder = this.sortKey === key ? -this.sortOrder : 1;
      this.sortKey = key;
      this.filteredUsers.sort((a, b) => {
        const aValue = a[key] ? a[key].toString().toLowerCase() : '';
        const bValue = b[key] ? b[key].toString().toLowerCase() : '';
        
        if (aValue < bValue) return -1 * this.sortOrder;
        if (aValue > bValue) return 1 * this.sortOrder;
        return 0;
      });
    }
  }
};
</script>

<style>
.top-section {
  background-color: #1e2952;
  padding: 40px;
  color: white;
  text-align: center;
}

.filter-section {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
}

.filter-controls {
  display: flex;
  align-items: center;
}

.filter-select, .filter-button, .reset-button {
  margin: 0 5px;
  padding: 10px;
  font-size: 16px;
  border-radius: 4px;
  border: 1px solid #ccc;
}
.filter-select {
    width: 630px;
}

.filter-button {
  background-color: #4caf50;
  color: white;
  cursor: pointer;
  border: none;
}

.reset-button {
  background-color: #f44336;
  color: white;
  cursor: pointer;
  border: none;
}

.search-section {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 10px;
}

.search-field {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin: 0 10px;
}

.search-input, .search-button {
  margin-top: 18px;
  padding: 10px;
  font-size: 16px;
  border-radius: 4px;
  border: 1px solid #ccc;
}

.search-button {
  background-color: #4caf50;
  color: white;
  cursor: pointer;
  border: none;
}

.table-container-users {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: calc(90vh - 40px - 40px);
  margin-top: -40px;
}

table {
  border-collapse: collapse;
  margin: 20px 0;
  font-size: 18px;
  min-width: 400px;
  background-color: #f9f9f9;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
}

table th, table td {
  padding: 12px 15px;
  text-align: left;
  cursor: pointer;
}

table th {
  background-color: #f2f2f2;
  font-weight: bold;
}

table tr:nth-of-type(even) {
  background-color: #f3f3f3;
}
</style>