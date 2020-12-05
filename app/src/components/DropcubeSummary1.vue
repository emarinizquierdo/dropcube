<template>
  <div class="dropcube-summary-one-wrapper">
    <div class="layout-row layout-align-center-center summary-title">
      <h3 class="flow-h flex header light">{{$t('SUMMARY1.TITLES.MAIN')}}</h3>
    </div>
    <div
      class="functions layout-row layout-xs-column layout-sm-column layout-margin layout-align-center-center layout-align-space-around"
    >
      <div class="flex-xs-100 flex-sm-100 flex-30">
        <div class="layout-column">
          <div on-mouseover="_rain">
            <div class="layout-row layout-margin">
              <div class="flex-start">
                <v-icon name="cloud-rain"/>
              </div>
              <div class="flex">
                <h5>{{$t('SUMMARY1.TITLES.RAIN')}}</h5>
                <p class="md-body-2">{{$t('SUMMARY1.DESCRIPTIONS.RAIN')}}</p>
              </div>
            </div>
          </div>
          <div on-mouseover="_snow">
            <div class="layout-row layout-margin">
              <div class="flex-start">
                <v-icon name="snowflake"/>
              </div>
              <div class="flex">
                <h5>{{$t('SUMMARY1.TITLES.SNOW')}}</h5>
                <p class="md-body-2">{{$t('SUMMARY1.DESCRIPTIONS.SNOW')}}</p>
              </div>
            </div>
          </div>
          <div on-mouseover="_snow">
            <div class="layout-row layout-margin">
              <div class="flex-start">
                <v-icon name="rainbow"/>
              </div>
              <div class="flex">
                <h5>{{$t('SUMMARY1.TITLES.SNOW')}}</h5>
                <p class="md-body-2">{{$t('SUMMARY1.DESCRIPTIONS.SNOW')}}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="flex-5" />
      <div class="flex-30">
        <iron-icon
          id="cubeColor"
          class="cube-icon"
          icon="dropcube-icons:cube-color" />
      </div>
      <div class="flex-5" />
      <div class="flex-xs-100 flex-sm-100 flex-30">
        <div on-mouseover="_wind">
          <div class="layout-row layout-margin">
            <div class="flex-start">
              <v-icon name="wind"/>
            </div>
            <div class="flex">
              <h5>{{$t('SUMMARY1.TITLES.WIND')}}</h5>
              <p class="md-body-2">{{$t('SUMMARY1.DESCRIPTIONS.WIND')}}</p>
            </div>
          </div>
        </div>
        <div on-mouseover="_hot">
          <div class="layout-row layout-margin">
            <div class="flex-start">
              <v-icon name="thermometer-full"/>
            </div>
            <div class="flex">
              <h5>{{$t('SUMMARY1.TITLES.HOT')}}</h5>
              <p class="md-body-2">{{$t('SUMMARY1.DESCRIPTIONS.HOT')}}</p>
            </div>
          </div>
        </div>
        <div on-mouseover="_snow">
          <div class="layout-row layout-margin">
            <div class="flex-start">
              <v-icon name="rainbow"/>
            </div>
            <div class="flex">
              <h5>{{$t('SUMMARY1.TITLES.SNOW')}}</h5>
              <p class="md-body-2">{{$t('SUMMARY1.DESCRIPTIONS.SNOW')}}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
.dropcube-summary-one-wrapper {
  margin-top: 50px;
  display: block;

  .summary-title {
    text-align: center;
  }

  .cube-icon {
    width: 100%;
    height: auto;
  }

  .weather-icon {
    width: 37px;
    height: auto;
  }

  .functions {
    cursor: pointer;
  }
}
</style>
