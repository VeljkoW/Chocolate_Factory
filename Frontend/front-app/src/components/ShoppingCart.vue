<template>
    <div class="cart-container">
        <div class="top-section">
            <button class="back-button" @click="goBack">Go back</button>
        </div>
        <div class="cart-items">
            <h2>Shopping Cart</h2>
            <table class="table">
                <thead>
                    <tr>
                        <th>Image</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Action</th> <!-- New column for remove button -->
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(item, index) in cartItems" :key="item.id">
                        <td><img :src="item.imagePath" alt="Chocolate Image" style="width: 100px; height: auto;" /></td>
                        <td>{{ item.name }}</td>
                        <td>{{ item.price }}</td>
                        <td>
                            <input type="number" v-model.number="item.quantity" min="1" :max="item.stock"
                                @change="updateQuantity(item, index)" @input="handleQuantityChange(item, index)" />
                        </td>
                        <td>
                            <button @click="removeFromCart(item.id)">Remove</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="cart-summary">
            <h2>Summary</h2>
            <p>Total Items: {{ totalItems }}</p>
            <p>Total Price: {{ totalPrice }}</p>
            <p>Discount: {{ discount }}%</p>
            <button @click="checkout">Checkout</button> <!-- New checkout button -->
        </div>
    </div>
</template>


<script>
import axios from 'axios';

export default {
    name: 'ShoppingCart',
    data() {
        return {
            cartItems: [], // Array to hold cart items { id, imagePath, name, price, quantity, stock }
            discount: 10, // Example discount (adjust as per your logic)
            totalItems: 0,
            totalPrice: 0
        };
    },
    mounted() {
        this.fetchCartItems();
    },
    methods: {
        checkout() {
            const token = localStorage.getItem('token');
            axios.post('http://localhost:8080/WebShopAppREST/rest/cart/checkout', { token }, {
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => {
                console.log('Checkout successful:', response);
                alert('Purchase successful!');
                this.$router.push('/');
                this.cartItems = []; // Clear cart items
                this.calculateSummary(); // Recalculate summary after clearing cart
            }).catch(error => {
                console.error('Error during checkout:', error);
                alert('Error during checkout. Please try again.');
            });
        },
        goBack() {
            this.$router.push('/');
        },
        fetchCartItems() {
            const token = localStorage.getItem('token');
            axios.post('http://localhost:8080/WebShopAppREST/rest/cart/fetch', { token }, {
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => {
                const cartItems = response.data.map(item => {
                    return {
                        id: item.key.id,
                        name: item.key.name,
                        price: item.key.price,
                        imagePath: item.key.imagePath,
                        quantity: item.value,
                        stock: item.key.stock
                    };
                });
                this.cartItems = cartItems;
                this.calculateSummary();
            }).catch(error => {
                console.error('Error fetching cart items:', error);
            });
        },
        handleQuantityChange(item, index) {
            let newQuantity = parseInt(item.quantity);
            if (newQuantity < 1) {
                newQuantity = 1;
            }
            if (newQuantity > item.stock) {
                newQuantity = item.stock;
            }
            this.cartItems[index].quantity = newQuantity;
            this.updateCartItem(item);
            this.calculateSummary();
        },
        updateQuantity(item, index) {
            const newQuantity = item.quantity;
            if (newQuantity >= 1 && newQuantity <= item.stock) {
                this.updateCartItem(item);
            } else {
                alert('Invalid quantity!');
                this.cartItems[index].quantity = item.quantity;
            }
            this.calculateSummary();
        },
        updateCartItem(item) {
            const token = localStorage.getItem('token');
            axios.put(`http://localhost:8080/WebShopAppREST/rest/cart/update/${item.id}`, {
                quantity: item.quantity,
                token: token
            }, {
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => {
                console.log('Cart item updated successfully:', response);
            }).catch(error => {
                console.error('Error updating cart item:', error);
            });
        },
        removeFromCart(itemId) {
            const token = { token: localStorage.getItem('token') };
            axios.delete(`http://localhost:8080/WebShopAppREST/rest/cart/delete/${itemId}`, {
                headers: {
                    'Content-Type': 'application/json'
                },
                data: token
            }).then(response => {
                console.log('Item removed successfully:', response);
                this.cartItems = this.cartItems.filter(item => item.id !== itemId);
                this.calculateSummary();
            }).catch(error => {
                console.error('Error removing item:', error);
            });
        },
        calculateSummary() {
            this.totalItems = this.cartItems.reduce((total, item) => {
                return total + item.quantity;
            }, 0);
            this.totalPrice = this.cartItems.reduce((total, item) => {
                return total + (item.price * item.quantity);
            }, 0);
            this.totalPrice -= (this.totalPrice * (this.discount / 100));
        }
    }
};
</script>

<style scoped>
.cart-container {
    display: flex;
    justify-content: space-between;
}

.cart-items {
    flex: 1;
    margin-right: 20px;
}

.cart-summary {
    flex: 1;
    background-color: #f0f0f0;
    padding: 20px;
    border: 1px solid #ccc;
}

.table {
    width: 100%;
    border-collapse: collapse;
}

.table th,
.table td {
    border: 1px solid #ddd;
    padding: 8px;
    text-align: left;
}

.top-section {
    background-color: #1e2952;
    padding: 10px 40px;
    color: white;
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 87px;
}
</style>
