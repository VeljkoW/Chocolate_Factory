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
    <div v-if="userRole !== 'Customer' && userRole !== ''">
      <div class="container mt-4">
        <div class="row justify-content-center">
          <div v-for="comment in comments" :key="comment.id" class="col-md-6 col-lg-4 mb-3">
            <div class="card">
              <div class="card-body">
                <h5 class="card-title">Grade: {{ comment.grade }}</h5>
                <p class="card-text">{{ comment.comment }}</p>
                <!-- Dodajte dugmad za odobravanje/odbijanje komentara -->
                <div v-if="userRole === 'Administrator' || userRole === 'Manager'">
                  <button v-if="comment.status === 'Zahtev'" @click="approveComment(comment.id)">Approve</button>
                  <button v-if="comment.status === 'Zahtev'" @click="rejectComment(comment.id)">Reject</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-else>
      <div class="container mt-4">
        <div class="row justify-content-center">
          <div v-for="comment in filteredComments" :key="comment.id" class="col-md-6 col-lg-4 mb-3">
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
    <div v-if="userRole === 'Customer' && canComment">
      <h2>Leave a Comment</h2>
      <table>
        <thead>
          <tr>
            <th>Grade</th>
            <th>Comment</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>
              <select v-model="newComment.grade" required>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
              </select>
            </td>
            <td>
              <textarea v-model="newComment.comment" rows="4" required></textarea>
            </td>
          </tr>
        </tbody>
      </table>
      <button @click="submitComment">Submit Comment</button>
    </div>
    <div class="purchases-section" v-if="managersFactory === id">
      <h2>Purchases</h2>
      <div name="Search">
        <table>
          <tbody>
            <tr>
              <td>
                <label for="priceFrom">Price from:</label>
                <input type="number" id="priceFrom" v-model.number="minPrice" @input="filterPurchases"
                  placeholder="Min Price">
              </td>
              <td>
                <label for="priceTo">Price to:</label>
                <input type="number" id="priceTo" v-model.number="maxPrice" @input="filterPurchases"
                  placeholder="Max Price">
              </td>
            </tr>
            <tr>
              <td>
                <label for="dateFrom">Date from:</label>
                <input type="date" id="dateFrom" v-model="startDate" @input="filterPurchases" placeholder="Start Date">
              </td>
              <td>
                <label for="dateTo">Date to:</label>
                <input type="date" id="dateTo" v-model="endDate" @input="filterPurchases" placeholder="End Date">
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <table class="purchase-table">
        <thead>
          <tr>
            <th>Date</th>
            <th>Price</th>
            <th>Status</th>
            <th>Chocolates</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="purchase in filteredPurchases" :key="purchase.id">
            <td>{{ purchase.date }}</td>
            <td>{{ purchase.price }}</td>
            <td>{{ purchase.status }}</td>
            <td>
              <ul>
                <li v-for="(quantity, name) in purchase.chocolates" :key="name">{{ name }}: {{ quantity }}</li>
              </ul>
            </td>
            <td>
              <div v-if="purchase.status === 'Obrada'">
                <button @click="approvePurchase(purchase.id)">Approve</button>
                <button @click="showRejectForm(purchase.id)">Reject</button>
              </div>
              <div v-if="rejectingPurchaseId === purchase.id">
                <textarea v-model="rejectReason" placeholder="Enter reason for rejection"></textarea>
                <button @click="rejectPurchase(purchase.id)">Submit</button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
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
      userid: -1,
      managersFactory: -1,
      purchases: [],
      rejectingPurchaseId: null,
      rejectReason: '',
      minPrice: null,
      maxPrice: null,
      startDate: null,
      endDate: null,
      filteredPurchases: [],
      newComment: {
        grade: '',
        comment: ''
      },
      canComment: false
    }
  },
  mounted() {
    this.getFactory();
    this.getPurchases();
    this.getRole();
  },
  computed: {
    filteredChocolates() {
      if (this.userRole === 'Customer') {
        return this.chocolates.filter(chocolate => chocolate.stock > 0);
      }
      return this.chocolates;
    },
    filteredPurchases() {
      return this.purchases.filter(purchase => {
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

        return meetsPriceCriteria && meetsDateCriteria;
      });
    },
    filteredComments() {
      return this.filterComments();
    }
  },
  methods:
  {
    async getRole() {
      const token = localStorage.getItem('token');
      this.token = token;
      if (token) {
        try {
          const response = await axios.post("http://localhost:8080/WebShopAppREST/rest/user/jwt/decode", {
            token
          }, {
            headers: {
              'Content-Type': 'application/json'
            }
          });
          if (response.status === 200) {
            const { id, username, role } = response.data;
            this.userRole = role;
            this.userid = id;
            this.getManagerFactory();
          } else {
            localStorage.removeItem('token');
          }
        } catch (error) {
          console.error('Error verifying token:', error);
          localStorage.removeItem('token');
        }
      }
    },
    async getManagerFactory() {
      const token = localStorage.getItem('token');
      if (token) {
        try {
          const response = await axios.get('http://localhost:8080/WebShopAppREST/rest/user/getManagerFactory', {
            headers: {
              'Authorization': `Bearer ${token}`
            },
            params: {
              userId: this.userid
            }
          });
          this.managersFactory = response.data.factoryId;
          console.log('Manager\'s Factory ID:', this.managersFactory);
          console.log('This Factory ID:', this.id);
          this.getPurchases()
        } catch (error) {
          console.error('Error fetching manager\'s factory:', error);
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
        const comments = response.data;
        const userId = this.userid;

        let userCommentExists = false;
            for (let i = 0; i < comments.length; i++) {
                if (comments[i].userId == userId && comments[i].factoryId == id) {
                    userCommentExists = true;
                    break;
                }
            }
        this.canComment = !userCommentExists;
        this.comments = response.data;


      }).catch(error => {
        alert(error.response.data);
      });
    },
    async submitComment() {
      try {
        const response = await axios.post('http://localhost:8080/WebShopAppREST/rest/comment/add', {
          userId: this.userid,
          factoryId: this.id,
          grade: this.newComment.grade,
          comment: this.newComment.comment
        });
        alert('Comment submitted successfully!');
        // Refresh comments after submission
        this.getComments();
        // Clear the form fields
        this.canComment = false;
        this.newComment.grade = '';
        this.newComment.comment = '';
      } catch (error) {
        alert('Error submitting comment: ' + error.response.data);
      }
    },
    filterPurchases() {
      this.filteredPurchases = this.purchases.filter(purchase => {
        // Apply filtering logic based on minPrice, maxPrice, startDate, endDate
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

        return meetsPriceCriteria && meetsDateCriteria;
      });
    },
    filterComments() {
      if (!this.userRole || this.userRole === 'Customer') {
        return this.comments.filter(comment => comment.status === 'Odobren');
      } else {
        return this.comments;
      }
    },
    async approveComment(commentId) {
      try {
        const response = await axios.post(`http://localhost:8080/WebShopAppREST/rest/comment/approveComment?commentId=${commentId}`);
        this.getComments();
      } catch (error) {
        alert(error.response.data);
      }
    },
    async rejectComment(commentId) {
      try {
        const response = await axios.post(`http://localhost:8080/WebShopAppREST/rest/comment/rejectComment?commentId=${commentId}`);
        this.getComments();
      } catch (error) {
        alert(error.response.data);
      }
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
        chocolate.stock -= quantity
      }).catch(error => {
        console.log(error)
        console.error('Error adding to cart:', error);
        if (error.response.status == 409) {
          alert("Empty your cart!!!")
        }
      });
    },
    getPurchases() {
      axios.get(`http://localhost:8080/WebShopAppREST/rest/purchase/getByFactoryId?factoryId=${this.id}`)
        .then(response => {
          this.purchases = response.data;
        })
        .catch(error => {
          alert("no purchases");
        });
    },
    approvePurchase(purchaseId) {
      axios.post(`http://localhost:8080/WebShopAppREST/rest/purchase/approvePurchase?purchaseId=${purchaseId}`)
        .then(response => {
          this.getPurchases();
        })
        .catch(error => {
          alert(error.response.data);
        });
    },
    showRejectForm(purchaseId) {
      this.rejectingPurchaseId = purchaseId;
    },
    rejectPurchase(purchaseId) {
      axios.post(`http://localhost:8080/WebShopAppREST/rest/purchase/rejectPurchase?purchaseId=${purchaseId}`)
        .then(response => {
          this.getPurchases();
          this.rejectingPurchaseId = null;
        })
        .catch(error => {
          alert(error.response.data);
        });
    },
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