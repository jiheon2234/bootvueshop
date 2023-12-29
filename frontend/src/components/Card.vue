<template>
  <div class="card shadow-sm">
    <span class="img" :style="{backgroundImage: `url(${item.imgPath})`}"/>

    <div class="card-body">
      <p>
        <span>{{ item.name }} &nbsp;</span>
        <span class="discount badge bg-danger ">{{ item.discountPer }}%</span>
      </p>
      <div class="d-flex justify-content-between align-items-center">
        <button class="btn btn-primary">구입화기</button>
        <small class="price text-muted">{{ lib.getNumberFormatted(item.price) }}원</small>
        <span
            class="real text-danger ">{{ lib.getNumberFormatted(item.price - (item.price * item.discountPer / 100)) }}₩</span>


      </div>
    </div>
  </div>
</template>
<script>
import lib from "@/scripts/lib";

export default {
  name: "Card",
  props: {
    item: Object
  },
  setup() {
    return {lib}
  }
}
</script>

<style scoped>
.card .img {
  display: inline-block;
  height: 250px;
  background-size: cover;
  background-position: center;
}

.card .card-body .price {
  text-decoration: line-through;
}
</style>