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
        <div class="filter-reset-buttons">
        <button class="filter-button" @click="filterUsers">Filter</button>
        <button class="reset-button" @click="resetFilter">Reset</button>
      </div>
      </div>
    </div>
    <div class="filter-sectionTypes">
      <div class="filter-controlsTypes">
        <select v-model="selectedType" class="filter-selectTypes">
          <option value="">All Types</option>
          <option value="None">None</option>
          <option value="Bronze">Bronze</option>
          <option value="Silver">Silver</option>
          <option value="Gold">Gold</option>
        </select>
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
            <th @click="sortUsers('type')">Type</th>
            <th>Block</th>
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
            <td>{{ getUserTypeName(user.userTypeId) }}</td>
            <td>
              <template v-if="user.uloga === 'Administrator'">
              </template>
              <template v-else>
                <template v-if="user.blocked">
                  <button class="unblock-button" @click="unblock(user.id)">Unblock</button>
                </template>
                <template v-else>
                  <button class="block-button" @click="block(user.id)">Block</button>
                </template>
              </template>
            </td>
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
      selectedType: '',
      searchUsername: '',
      searchName: '',
      searchSurname: '',
      filteredUsers: [],
      sortKey: '',
      sortOrder: 1,
      usertypes: [],
      role:"",
    };
  },
  created() {
    this.getUsers();
    this.getRole();
    this.getUserTypes();
  },
  methods: {
    async getUsers() {
      try {
        const response = await axios.get('http://localhost:8080/WebShopAppREST/rest/user/all');
        this.users = response.data;
        this.filteredUsers = this.users;
      } catch (error) {
        console.error('Error fetching users:', error);
        alert('Failed to fetch users. Please try again later.');
      }
    },
    async getRole(){
      const token = localStorage.getItem('token');
      console.log(token);
      if (token) {
          const response = await axios.post("http://localhost:8080/WebShopAppREST/rest/user/jwt/decode", {
            token
          }, {
            headers: {
              'Content-Type': 'application/json'
            }
          });
          console.log(response);
          if (response.status === 200) {
            let { id, username, role } = response.data;
            this.role=role;
            console.log(role)
          }
        }
      },
    filterUsers() {
      this.filteredUsers = this.users.filter(user => {
        const matchesRole = this.selectedRole === '' || user.uloga === this.selectedRole;
        const matchesType = this.selectedType === '' || this.getUserTypeName(user.userTypeId) === this.selectedType;
        const matchesUsername = this.searchUsername === '' || user.username.toLowerCase().includes(this.searchUsername.toLowerCase());
        const matchesName = this.searchName === '' || user.name.toLowerCase().includes(this.searchName.toLowerCase());
        const matchesSurname = this.searchSurname === '' || user.surname.toLowerCase().includes(this.searchSurname.toLowerCase());
        return matchesRole && matchesUsername && matchesName && matchesSurname && matchesType;
      });
    },
    resetFilter() {
      this.selectedRole = '';
      this.selectedType = '';
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
    },
    getUserTypes()
    {
      axios.get('http://localhost:8080/WebShopAppREST/rest/usertype/getall').then(response => this.usertypes = response.data).catch(error => {
                alert('Error fetching usertypes');
            });
    },
    getUserTypeName(userTypeId) {
      const userType = this.usertypes.find(u => u.id === userTypeId);
      return userType ? userType.name : 'Unknown';
    },
    block(id)
    {
      if(id == null)
      {
        alert("User doesnt exist!");
      }
      axios.put('http://localhost:8080/WebShopAppREST/rest/user/blockUser?id=' + id).then(response => {
        const user = this.filteredUsers.find(user => user.id === id);
        if (user) {
          user.blocked = true;
        }
      }).catch(error => {
                alert('Error');
            });

    },
    unblock(id)
    {
      if(id == null)
    {
      alert("User doesnt exist!");
    }
    axios.put('http://localhost:8080/WebShopAppREST/rest/user/unblockUser?id=' + id).then(response => {
        const user = this.filteredUsers.find(user => user.id === id);
        if (user) {
          user.blocked = false;
        }
      }).catch(error => {
                alert('Error');
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
.block-button {
  background-color: red !important;
  color: white !important;
  padding: 5px 18px !important;
  font-size: 14px !important;
  border-radius: 4px !important;
  border: none !important;
  cursor: pointer !important;
  margin: 0 !important;
}

.block-button:hover {
  background-color: darkred !important;
}

.unblock-button {
  background-color: darkred !important;
  color: white !important;
  padding: 5px 10px !important;
  font-size: 14px !important;
  border-radius: 4px !important;
  border: none !important;
  cursor: pointer !important;
  margin: 0 !important;
}

.unblock-button:hover {
  background-color: darkred !important;
}
.filter-section {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 50px;
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
.filter-sectionTypes {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
  margin-right: 135px;
}

.filter-controlsTypes {
  display: flex;
  align-items: center;
}

.filter-selectTypes{
  margin: 0 5px;
  padding: 10px;
  font-size: 16px;
  border-radius: 4px;
  border: 1px solid #ccc;
}
.filter-selectTypes {
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
.reset-button:hover {
  background-color: darkred;
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
  margin-top: 20px;
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