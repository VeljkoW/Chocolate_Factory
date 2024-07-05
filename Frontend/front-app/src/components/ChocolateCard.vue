<template>
    <div v-if="chocolate" class="card-container-chocolate">
        <table class="card-chocolate">
            <tr>
                <td colspan="2" class="image-container-chocolate">
                    <img :src="chocolate.imagePath" alt="Chocolate Image" class="chocolate-image"/>
                </td>
            </tr>
            <tr>
                <td colspan="2" class="container-chocolate" @click="navigateToChocolate(chocolate.id)">
                    <h2>{{ chocolate.name }}</h2>
                    <p>Price: {{ chocolate.price }}</p>
                </td>
            </tr>
            <tr v-if="userRole === 'Customer'">
                <td colspan="2"><strong>Stock:</strong> {{ chocolate.stock }}</td>
            </tr>
            <tr v-if="userRole === 'Customer'">
                <td>
                    <input 
                        type="number" 
                        v-model.number="quantity" 
                        min="1" 
                        :max="chocolate.stock" 
                        @input="checkQuantity"
                    />
                </td>
                <td>
                    <button @click="addToCart">Add</button>
                </td>
            </tr>
        </table>
    </div>
    <div v-else>
        Loading...
    </div>
</template>

<script>
export default {
    name: 'ChocolateCard',
    props: {
        chocolate: {
            type: Object,
            required: true
        },
        userRole: {
            type: String,
            required: true
        }
    },
    data() {
        return {
            quantity: 1,
        };
    },
    methods: {
        addToCart() {
            if (this.quantity <= this.chocolate.stock) {
                this.$emit('add-to-cart', {
                    chocolate: this.chocolate,
                    quantity: this.quantity
                });
            } else {
                alert('Quantity exceeds stock available');
            }
        },
        navigateToChocolate(chocolateId)
        {
            this.$router.push("/chocolate/" + chocolateId);
        },
        checkQuantity() {
            if (this.quantity < 1) {
                this.quantity = 1;
            }
            else if(this.quantity>this.chocolate.stock){
              this.quantity = this.chocolate.stock
            }
        },
    }
}
</script>
<style>
.card-chocolate {
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
  transition: 0.3s;
  border-radius: 5px;
  max-width: 200px;
  height: 300px;
  width: 200px;
  margin: 0 auto;
}

.card-chocolate:hover {
  box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2);
}

.card-container-chocolate {
  padding: 2px 16px;
}

.image-container-chocolate {
  width: 100%;
  height: 150px;
  overflow: hidden;
  border-radius: 5px 5px 0 0;
}

.chocolate-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.container-chocolate {
  padding: 16px;
  text-align: center;
}

.chocolate-name {
  font-size: 1.2em;
  margin: 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.container {
    padding:2px 16px;
}
.card {
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
    transition: 0.3s;
    border-radius: 5px;
}
.card:hover {
    box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);
    
}
.card-container {
    padding: 2px 16px;
    max-width: 400px;
}
</style>