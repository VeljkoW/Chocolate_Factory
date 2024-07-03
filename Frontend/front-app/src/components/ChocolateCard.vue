<template>
    <div v-if="chocolate" class="card-container-chocolate">
        <div class="card-chocolate">
        <div class="image-container-chocolate">
          <img :src="chocolate.imagePath" alt="Chocolate Image" class="chocolate-image"/>
        </div>
          <div class="container-chocolate">
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