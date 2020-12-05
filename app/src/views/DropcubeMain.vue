<template>
  <div class="dropcube-main-wrapper">
    <dropcube-intro />
    <dropcube-summary-1 />
  </div>
</template>

<script>
import DropcubeIntro from "@/components/DropcubeIntro.vue";
import DropcubeSummary1 from "@/components/DropcubeSummary1.vue";
export default {
  components: {
    DropcubeIntro,
    DropcubeSummary1
  }
};
</script>

<style lang="scss" scoped>
.dropcube-main-wrapper {
  padding-top: 212px;
}
</style>
