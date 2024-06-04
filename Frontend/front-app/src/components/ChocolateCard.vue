<template>
    <div v-if="chocolate" class="card-container">
        <div class="card">
          <img :src="chocolate.imagePath" alt="Chocolate Image" class="chocolate-image"/>
          <div class="container">
            <h2>{{ chocolate.name }}</h2>
            <p>Price: {{ chocolate.price }}</p>
          </div>
        </div>
      </div>
      <div v-else>
        Loading...
      </div>
</template>
<script>
export default {
    name: 'ChocolateCard',
    props: {
        chocolate: {}
    }
}
</script>
<style>

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

.chocolate-image {
    width: 100px;
    height: 100px;
    border-radius: 5px 5px 0 0;
}
</style>