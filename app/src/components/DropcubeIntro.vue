<template>
  <div class="dropcube-intro-wrapper layout horizontal center justified">
    <div class="layout-row layout-margin layout-align-center-center layout-align-space-around">
      <div class="hide-xs flex-20">
        <v-icon name="wind"/>
      </div>
      <div class="flex-xs-100 flex-60">
        <h4 class="flow-h">{{$t('INTRO.TITLE')}}</h4>
        <p class="flow-text">{{$t('INTRO.DESCRIPTION')}}</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
.dropcube-intro-wrapper {
  background-color: var(--dropcube-grey-block);
  display: block;

  h4 {
    color: var(--dropcube-grey-text);
  }

  dropcube-container {
    height: 330px;
  }

  .weather-cool {
    width: 100%;
    height: auto;
  }
}
</style>
