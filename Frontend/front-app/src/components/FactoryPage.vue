<template>
  <div>
    <div class="top-section">
      <h1>{{ name }}</h1>
      <img :src="logoImagePath" alt="Factory Logo" class="factory-logo" />
      <button class="add-button" @click="addChocolate">Add Chocolate</button>
      <button class="addEmployee-button" @click="addEmployee">Add Employee</button>
      <button class="back-button" @click="goBack">Go back</button>
    </div>
    <div class="factory-details">
      <h2>Factory Details</h2>
      <p><strong>Opening Time :</strong> {{ openingTime }}</p>
      <p><strong>Closing Time:</strong> {{ closingTime }}</p>
      <p><strong>Status:</strong> {{ status }}</p>
      <p><strong>Grade:</strong> {{ grade }}</p>

      <h2>Location Details</h2>
      <p><strong>Latitude:</strong> {{ latitude }}</p>
      <p><strong>Longitude:</strong> {{ longitude }}</p>

      <h2>Address</h2>
      <p><strong>City:</strong> {{ city }}</p>
      <p><strong>Street:</strong> {{ street }}</p>
      <p><strong>Number:</strong> {{ number }}</p>
      <p><strong>Postal Code:</strong> {{ postalCode }}</p>
    </div>
    <div class="chocolate-cards">
      <div v-for="chocolate in filteredChocolates" :key="chocolate.id" class="chocolate-card-link">
        <ChocolateCard :chocolate="chocolate" :userRole="userRole" @add-to-cart="handleAddToCart" />
      </div>
    </div>
    <div>
      <div class="container mt-4">
        <div class="row justify-content-center">
          <div v-for="comment in comments" :key="comment.id" class="col-md-6 col-lg-4 mb-3">
            <div class="card">
              <div class="card-body">
                <h5 class="card-title">Grade: {{ comment.grade }}</h5>
                <p class="card-text">{{ comment.comment }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from 'axios';
import ChocolateCard from './ChocolateCard.vue';

export default {
  name: 'FactoryPage',
  components: {
    ChocolateCard
  },
  data() {
    return {
      id: 0,
      locationId: 0,
      addressId: 0,
      name: '',
      openingTime: '',
      closingTime: '',
      status: '',
      latitude: 0,
      longitude: 0,
      city: '',
      street: '',
      postalCode: '',
      number: 0,
      logoImagePath: '',
      grade: 0,
      chocolates: [],
      comments: [],
      userRole: '',
      userid: -1
    }
  },
  mounted() {
    this.getRole();
    this.getFactory();
  },
  computed: {
    filteredChocolates() {
      if (this.userRole === 'Customer') {
        return this.chocolates.filter(chocolate => chocolate.stock > 0);
      }
      return this.chocolates;
    }
  },
  methods:
  {
    async getRole() {
      const token = localStorage.getItem('token');
      this.token = token;
      console.log(token);
      if (token) {
        try {
          const response = await axios.post("http://localhost:8080/WebShopAppREST/rest/user/jwt/decode", {
            token
          }, {
            headers: {
              'Content-Type': 'application/json'
            }
          });
          console.log(response);
          if (response.status === 200) {
            const { id, username, role } = response.data;
            this.userRole = role;
            this.userid = id;
          } else {
            localStorage.removeItem('token');
          }
        } catch (error) {
          console.error('Error verifying token:', error);
          localStorage.removeItem('token');
        }
      }
    },
    getFactory() {
      let id = this.$route.params.id;
      if (id == null) {
        alert('factory not found');
        return;
      }

      axios.get('http://localhost:8080/WebShopAppREST/rest/factory/GetById?id=' + id).then(response => {
        this.id = response.data.id;
        this.name = response.data.name;
        this.openingTime = response.data.openingTime;
        this.closingTime = response.data.closingTime;
        this.status = response.data.status;
        this.locationId = response.data.locationId;
        this.logoImagePath = response.data.logoImagePath;
        this.grade = response.data.grade;
        this.getLocation();
      }).catch(error => {
        alert(error.response.data);
      });

    },
    getLocation() {
      if (this.locationId === 0) {
        alert('location not found' + this.locationId);
        return;
      }
      axios.get('http://localhost:8080/WebShopAppREST/rest/location/GetById?id=' + this.locationId).then(response => {
        this.latitude = response.data.latitude;
        this.longitude = response.data.longitude;
        this.addressId = response.data.addressId;
        this.getAddress();
      }).catch(error => {
        alert(error.response.data);
      });
    },
    getAddress() {
      if (this.addressId === 0) {
        alert('address not found');
        return;
      }
      axios.get('http://localhost:8080/WebShopAppREST/rest/address/GetById?id=' + this.addressId).then(response => {
        this.city = response.data.place;
        this.street = response.data.street;
        this.number = response.data.number;
        this.postalCode = response.data.postalCode;
        this.getChocolates();
      }).catch(error => {
        alert(error.response.data);
      });
    },
    getChocolates() {
      axios.get('http://localhost:8080/WebShopAppREST/rest/chocolate/getByFactoryId?factoryId=' + this.id).then(response => {
        this.chocolates = response.data;
        this.getComments();
      }).catch(error => {
        alert(error.response.data);
      });
    },
    getComments() {
      let id = this.$route.params.id;
      if (id == null) {
        alert('factory not found');
        return;
      }
      axios.get('http://localhost:8080/WebShopAppREST/rest/comment/getByFactoryId?factoryId=' + id).then(response => {
        this.comments = response.data;
        console.log(this.comments);
      }).catch(error => {
        alert(error.response.data);
      });
    },
    navigateToChocolate(chocolateId) {
      this.$router.push("/chocolate/" + chocolateId);
    },
    addChocolate() {
      this.$router.push("/addChocolate/" + this.id);
    },
    addEmployee() {
      this.$router.push("/createEmployee/" + this.id)
    },
    goBack() {
      this.$router.push("/");
    },
    handleAddToCart({ chocolate, quantity }) {
      const token = localStorage.getItem('token');

      axios.post('http://localhost:8080/WebShopAppREST/rest/cart/add', {
        chocolate: chocolate,
        quantity: quantity,
        token: token
      }, {
        headers: {
          'Content-Type': 'application/json'
        }
      }).then(response => {
        console.log(`Successfully added ${quantity} of ${chocolate.name} to the cart.`);
        console.log(response)
        chocolate.stock-=quantity
      }).catch(error => {
        console.log(error)
        console.error('Error adding to cart:', error);
        if(error.response.status==409){
          alert("Empty your cart!!!")
        }
      });
    }
  }
}

</script>
<style>
.top-section {
  background-color: #1e2952;
  padding: 40px;
  color: white;
  text-align: center;
}

.factory-logo {
  max-width: 100%;
  height: 300px;
  margin-top: 20px;
}

.factory-details {
  padding: 20px;
  margin: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
}

.factory-details h2 {
  margin-top: 0;
}

.factory-details p {
  margin: 5px 0;
}

.chocolate-card-link {
  text-decoration: none;
  color: inherit;
}

.chocolate-cards {
  display: flex;
  flex-wrap: wrap;
  gap: 50px;
  margin: 50px;
}

.chocolate-cards .chocolate-card {
  width: 300px;
  margin: 10px;
}

.addEmployee-button {
  background-color: #4caf50;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  cursor: pointer;
  border: none;
  border-radius: 4px;
  position: absolute;
  top: 20px;
  right: 200px;

}

.add-button {
  background-color: #4caf50;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  cursor: pointer;
  border: none;
  border-radius: 4px;
  position: absolute;
  top: 20px;
  right: 20px;
}

.add-button:hover {
  background-color: #45a049;
}

.back-button {
  background-color: #4caf50;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  cursor: pointer;
  border: none;
  border-radius: 4px;
  position: absolute;
  top: 20px;
  left: 20px;
}

.back-button:hover {
  background-color: #45a049;
}
</style>