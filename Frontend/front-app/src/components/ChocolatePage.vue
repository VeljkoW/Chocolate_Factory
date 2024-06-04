<template>
<div>
    <div class="top-section">
        <h1>Chocolate</h1>
        <button class="back-button" @click="goBack">Go back</button>
    </div>
    <div class="table-container">
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
        <button @click="EditChocolate">Edit Chocolate</button>
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
        }
    },
    mounted()
    {
        this.getChocolate();
    },
    methods:
    {
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
            }).catch(error => {
                alert(error.response.data);
            });
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
</style>