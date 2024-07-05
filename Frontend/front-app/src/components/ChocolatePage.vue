<template>
<div>
    <div class="top-section">
        <h1>Chocolate</h1>
        <button class="back-button" @click="goBack">Go back</button>
    </div>
    <div class="table-container-chocolatepage">
        <table>
            <tr>
                <td>
                    Name :
                </td>
                <td>
                    {{ name }}
                </td>
            </tr>
            <tr>
                <td>
                    Price :
                </td>
                <td>
                    {{ price }}
                </td>
            </tr>
            <tr>
                <td>
                    Variety :
                </td>
                <td>
                    {{ variety }}
                </td>
            </tr>
            <tr>
                <td>
                    Type :
                </td>
                <td>
                    {{ type }}
                </td>
            </tr>
            <tr>
                <td>
                    Mass :
                </td>
                <td>
                    {{ mass }}g
                </td>
            </tr>
            <tr>
                <td>
                    Description :
                </td>
                <td>
                    {{ description }}
                </td>
            </tr>
            <tr>
                <td>
                    Status :
                </td>
                <td>
                    {{ status }}
                </td>
            </tr>
            <tr>
                <td>
                    Stock :
                </td>
                <td>
                    {{ stock }}
                </td>
            </tr>
            <tr>
                <td>
                    Image :
                </td>
                <td>
                    {{ image }}
                </td>
            </tr>
        </table>
        <table v-if="showEditQuantity">
            <tr>
                <td>
                    <input type="number" v-model="stock">
                </td>
            </tr>
        </table>
        <button v-if="this.role == 'Manager'" @click="EditChocolate">Edit Chocolate</button>
        <button v-if="this.role == 'Administrator'" @click="EditQuantity">Edit Chocolate</button>
        <button @click="DeleteChocolate">Delete Chocolate</button>
    </div>
</div>
</template>
<script>
import axios from 'axios';

export default {
    name: 'ChocolatePage',
    data()
    {
        return {
            name: '',
            price: 0,
            variety: '',
            type: 'Milk',
            mass: 0,
            description: '',
            status: 'Unavailable',
            image: '',
            stock: 0,
            factoryId: 0,
            role:"",
            userid: -1,
            showEditQuantity:false,
            chocolateId:-1,
        }
    },
    mounted()
    {
        this.getRole();
        this.getChocolate();
    },
    methods:
    {
        EditQuantity(){
            this.chocolateId=this.$route.params.id
            if(!this.showEditQuantity)
            this.showEditQuantity=true
            else{
                this.showEditQuantity=false
                axios.post('http://localhost:8080/WebShopAppREST/rest/chocolate/update', {
                id: this.chocolateId,
                name: this.name,
                price: this.price,
                variety: this.variety,
                factoryId: this.factoryId,
                type: this.type,
                mass: this.mass,
                description: this.description,
                imagePath: this.image,
                status: this.status,
                stock: this.stock,
            }).then(response => {
                this.$router.push("/chocolate/" + this.chocolateId);
            }).catch(error => {
                alert(error.response.data);
            });
                
            }
        },
        getChocolate()
        {
            let chocolateId = this.$route.params.id;
            if(chocolateId == null)
            {
                alert('factory not found');
                return;
            }
            axios.get('http://localhost:8080/WebShopAppREST/rest/chocolate/getById?id=' + chocolateId).then(response => {
                this.name = response.data.name;
                this.price = response.data.price;
                this.variety = response.data.variety;
                this.type = response.data.type;
                this.mass = response.data.mass;
                this.description = response.data.description;
                this.status = response.data.status;
                this.image = response.data.imagePath;
                this.stock = response.data.stock;
                this.factoryId = response.data.factoryId;
                this.quantity=response.data.stock;
            }).catch(error => {
                alert(error.response.data);
            });
        },
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
                        this.role = role;
                        this.userid = id;
                    } else {
                        localStorage.removeItem('token');
                    }
                } catch (error) {
                    console.error('Error verifying token:', error);
                    localStorage.removeItem('token');
                }
                console.log(this.role);
            }
        },
        EditChocolate()
        {
            let chocolateId = this.$route.params.id;
            if(chocolateId == null)
            {
                alert('factory not found');
                return;
            }
            this.$router.push("/editChocolate/" + chocolateId);
        }, 
        DeleteChocolate()
        {
            let chocolateId = this.$route.params.id;
            console.log('http://localhost:8080/WebShopAppREST/rest/chocolate/' + chocolateId);
            if(chocolateId == null)
            {
                alert('Chocolate not found');
                return;
            }
            axios.delete('http://localhost:8080/WebShopAppREST/rest/chocolate/' + chocolateId)
                .then(response => {
                    alert('Chocolate deleted successfully');
                    this.goBack();
                })
                .catch(error => {
                    alert('Error deleting chocolate: ');
                });
        },
        goBack()
        {
            this.$router.push("/factory/" + this.factoryId);
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

.table-container-chocolatepage {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: calc(100vh - 40px - 40px);
    margin-top: -80px;
  }
</style>