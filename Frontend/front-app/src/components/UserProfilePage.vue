<template>
  <div class="top-section">
    <h1>{{ username }}'s Profile</h1>
    <button class="back-button" @click="goBack">Go back</button>
  </div>
  <div class="table-container-profile">
    <form @submit.prevent="edit">
      <table>
        <tr>
          <td>Name:</td>
          <td>{{ name }}</td>
        </tr>
        <tr>
          <td>Surname:</td>
          <td>{{ surname }}</td>
        </tr>
        <tr>
          <td>Gender:</td>
          <td>{{ gender }}</td>
        </tr>
        <tr>
          <td>Date of Birth:</td>
          <td>{{ dateOfBirth }}</td>
        </tr>
        <tr>
          <td>Username:</td>
          <td>{{ username }}</td>
        </tr>
        <tr>
          <td>Role:</td>
          <td>{{ uloga }}</td>
        </tr>
        <tr>
          <td>Points:</td>
          <td>{{ points }}</td>
        </tr>
        <tr>
          <td>User type:</td>
          <td>{{ getUserTypeName(userTypeId) }}</td>
        </tr>
      </table>
      <button type="submit">Edit Profile</button>
    </form>

    <!-- Purchases table -->
    <div class="purchases-table-container" v-if="uloga === 'Customer'">
      <h2>Your Purchases</h2>
      <div class="search-bar">
        <!-- Search filters -->
        <label for="factoryNameSearch">Search by Factory Name:</label>
        <input type="text" id="factoryNameSearch" v-model="factoryNameSearch" placeholder="Enter factory name">

        <label for="priceFrom">Price from:</label>
        <input type="number" id="priceFrom" v-model.number="minPrice" placeholder="Min Price">

        <label for="priceTo">Price to:</label>
        <input type="number" id="priceTo" v-model.number="maxPrice" placeholder="Max Price">

        <label for="dateFrom">Date from:</label>
        <input type="date" id="dateFrom" v-model="startDate" placeholder="Start Date">

        <label for="dateTo">Date to:</label>
        <input type="date" id="dateTo" v-model="endDate" placeholder="End Date">
      </div>
      <!-- Purchases table -->
      <table v-if="filteredPurchases.length">
        <thead>
          <tr>
            <th>Factory Name</th>
            <th>Chocolates</th>
            <th>Date</th>
            <th>Price</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(purchase, index) in filteredPurchases" :key="index">
            <td>{{ purchase.factoryName }}</td>
            <td>
              <ul>
                <li v-for="(count, name) in purchase.chocolates" :key="name">
                  {{ name }} ({{ count }})
                </li>
              </ul>
            </td>
            <td>{{ purchase.date }}</td>
            <td>{{ purchase.price }}</td>
            <td>{{ purchase.status }}</td>
            <td>
              <button @click="cancelOrder(purchase.id)" v-if="purchase.status === 'Obrada'">Cancel Order</button>
            </td>
          </tr>
        </tbody>
      </table>
      <p v-else>No purchases found.</p>
    </div>

    <!-- Delete account button -->
    <div>
      <button class="delete-account-button" @click="confirmDeleteAccount">Delete account</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      id: -1,
      name: '',
      surname: '',
      username: '',
      gender: '',
      dateOfBirth: '',
      uloga: '',
      points: 0,
      userTypeId: -1,
      userType: '',
      usertypes: [],
      purchases: [],
      factoryNameSearch: '',
      minPrice: null,
      maxPrice: null,
      startDate: '',
      endDate: '',
    }
  },
  computed: {
    filteredPurchases() {
      if (!this.factoryNameSearch && !this.minPrice && !this.maxPrice && !this.startDate && !this.endDate) {
        return this.purchases; // Return all purchases if no filters applied
      }

      return this.purchases.filter(purchase => {
        const factoryNameMatch = purchase.factoryName.toLowerCase().includes(this.factoryNameSearch.toLowerCase());
        const meetsPriceCriteria = (
          (!this.minPrice || purchase.price >= this.minPrice) &&
          (!this.maxPrice || purchase.price <= this.maxPrice)
        );

        const startDate = this.startDate ? new Date(this.startDate) : null;
        const endDate = this.endDate ? new Date(this.endDate) : null;
        const purchaseDate = new Date(purchase.date);

        const meetsDateCriteria = (
          (!startDate || purchaseDate >= startDate) &&
          (!endDate || purchaseDate <= endDate)
        );

        return factoryNameMatch && meetsPriceCriteria && meetsDateCriteria;
      });
    },
  },
  mounted() {
    this.getUserTypes();
    this.getUser();
    this.getPurchases();
  },
  methods: {
    goBack() {
      this.$router.push('/');
    },
    getUser() {
      this.id = this.$route.params.id
      if (this.id == -1) {
        alert('User not found');
        return;
      }
      axios.get('http://localhost:8080/WebShopAppREST/rest/user/getById?id=' + this.id)
        .then(response => {
          this.name = response.data.name;
          this.surname = response.data.surname;
          this.username = response.data.username;
          this.dateOfBirth = response.data.dateOfBirth;
          this.gender = response.data.gender;
          this.uloga = response.data.uloga;
          this.points = response.data.points;
          this.userTypeId = response.data.userTypeId;
        }).catch(error => {
          alert(error.response.data);
        });
    },
    getUserTypeName(userTypeId) {
      const userType = this.usertypes.find(u => u.id === userTypeId);
      return userType ? userType.name : 'Unknown';
    },
    getUserTypes() {
      axios.get('http://localhost:8080/WebShopAppREST/rest/usertype/getall')
        .then(response => {
          this.usertypes = response.data;
          console.log(response)
        }).catch(error => {
          alert('Error fetching user types');
        });
    },
    async edit() {
      this.$router.push('/edituserprofile/' + this.id);
    },
    confirmDeleteAccount() {
      if (confirm('Are you sure you want to delete your account?')) {
        this.deleteAccount();
      }
    },
    deleteAccount() {
      axios.put('http://localhost:8080/WebShopAppREST/rest/user/deleteUser?id=' + this.id)
        .then(response => {
          this.$router.push('/');
        }).catch(error => {
          alert('Error deleting user');
        });
    },
    getPurchases() {
      axios.get('http://localhost:8080/WebShopAppREST/rest/purchase/getByUserId', {
        params: {
          userId: this.id
        }
      }).then(response => {
        this.purchases = response.data;
      }).catch(error => {
        alert('Error fetching purchases');
      });
    },
    cancelOrder(purchaseId) {
      if (confirm('Are you sure you want to cancel this order?')) {
        axios.post('http://localhost:8080/WebShopAppREST/rest/purchase/cancelPurchase/' + purchaseId)
          .then(response => {
            this.getPurchases();
          }).catch(error => {
            alert('Error cancelling order');
          });
      }
    }
  }
}
</script>

<style>
.table-container-profile {
  margin: auto; /* Center horizontally */
  width: 80%; /* Adjust width as needed */
  height: calc(100vh - 80px); /* Adjust height calculation based on your needs */
  text-align: center; /* Center the content horizontally */
}


.delete-account-button {
  background-color: red !important;
  color: white !important;
  border-radius: 4px !important;
  border: none !important;
  cursor: pointer !important;
  margin: 30px !important;
}

.delete-account-button:hover {
  background-color: darkred !important;
}

.purchases-table-container {
  margin-top: 20px;
}

.purchases-table-container table {
  width: 100%;
  border-collapse: collapse;
}

.purchases-table-container th,
.purchases-table-container td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}
</style>
