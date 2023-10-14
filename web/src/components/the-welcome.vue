<template>
  <a-row >
    <a-col :span="24" >
      <a-card>
        <a-row >
          <a-col :span="8">

            <a-statistic title="总阅读数" :value="statisticResp.viewCount"  >
              <template #suffix>
                <UserOutlined />
              </template>
            </a-statistic>
          </a-col>
          <a-col :span="8">
            <a-statistic title="总点赞数" :precision="2" :value="statisticResp.voteCount">
              <template #suffix>
                <like-outlined />
              </template>
            </a-statistic>
          </a-col>
          <a-col :span="8">
            <a-statistic title="点赞率"
                         :precision="2"
                         :value="statisticResp.voteCount / statisticResp.viewCount  * 100"
                         suffix="%"
                         :value-style="{ color: '#cf1322' }"

            >
              <template #suffix>
                <like-outlined />
              </template>
            </a-statistic>
          </a-col>
        </a-row>

      </a-card>

    </a-col>

  </a-row>

  <br>
  <a-row :gutter="10">
    <a-col :span="12" >
      <a-card>
        <a-row >
          <a-col :span="12">
            <a-statistic title="今日阅读" :value="statisticResp.tadayViewCount"  >
              <template #suffix>
                <UserOutlined />
              </template>
            </a-statistic>
          </a-col>
          <a-col :span="12">
            <a-statistic title="今日点赞" :precision="2" :value="statisticResp.tadayVoteCount" >
              <template #suffix>
                <like-outlined />
              </template>
            </a-statistic>
          </a-col>
        </a-row>

      </a-card>

    </a-col>
    <a-col :span="12" >
      <a-card>
        <a-row >
          <a-col :span="12">
            <a-statistic title="预计今日阅读"
                         :value="statisticResp.todayViewIncreaseRateAbs"
                         :value-style="{ color: '#0000ff' }"

            >
              <template #suffix>
                <UserOutlined />
              </template>
            </a-statistic>
          </a-col>
          <a-col :span="12">
            <a-statistic title="预计今日阅读增长" :precision="2" :value="111"
                         suffix="%"
                         class="demo-class"
                         :value-style="statisticResp.todayViewIncreaseRate < 0 ? { color: '#3f8600' } : { color: '#cf1322' }"
            >
              <template #prefix>
                <arrow-down-outlined v-if="statisticResp.todayViewIncreaseRate < 0"/>
                <arrow-up-outlined v-if="statisticResp.todayViewIncreaseRate >= 0"/>
              </template>
            </a-statistic>
          </a-col>
        </a-row>

      </a-card>

    </a-col>
  </a-row>
  <br>
  <a-row>
    <a-col :span="24">

      <!-- 为 ECharts 准备一个定义了宽高的 DOM -->
      <div id="main" style="width: 600px;height:400px;"></div>
    </a-col>
  </a-row>
  <br>
  <a-row>
    <a-col :span="24" >
      <div id="main-col" style="width: 100%;height:300px;"></div>
    </a-col>
  </a-row>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import axios from "axios";
declare let echarts: any

export default defineComponent({

  name: 'the-welcome',

  setup(){

    const statisticResp = ref();
    statisticResp.value = {}

    const getStatistic = ()=>{
      axios.get("/ebook-snapshot/statistic"
      ).then((response)=>{
        const data = response.data;
        if (data.success) {
          statisticResp.value.viewCount = data.content[1].viewCount;
          statisticResp.value.voteCount = parseInt(data.content[1].voteCount).toFixed(0);
          statisticResp.value.tadayViewCount = data.content[1].viewIncrease;
          statisticResp.value.tadayVoteCount = parseInt(data.content[1].voteIncrease).toFixed(0);

          // 按分钟计算当前时间点，占一天的百分比
          const now = new Date();
          const nowRate = (now.getHours() * 60 + now.getMinutes()) / (60 * 24);
          // console.log(nowRate)

          statisticResp.value.todayViewIncrease = parseInt(String(data.content[1].viewIncrease / nowRate));
          // todayViewIncreaseRate：今日预计增长率
          statisticResp.value.todayViewIncreaseRate = ((statisticResp.value.todayViewIncrease - data.content[0].viewIncrease) / data.content[0].viewIncrease * 100).toFixed(2);
          statisticResp.value.todayViewIncreaseRateAbs = (Math.abs(statisticResp.value.todayViewIncreaseRate)).toFixed(2);
        }
      })
    }


    const init30Statistic = (list: any)=>{
      // 发布生产后出现问题：切到别的页面，再切回首页，报表显示不出来
      // 解决方法：把原来的id=main的区域清空，重新初始化
      const chartDom = document.getElementById('main-col');
      const myChart1 = echarts.init(chartDom);
      const xAxis = [];
      const seriesView = []
      const seriesVote = []
      console.log("____" + list[0].viewCount)

      for (let i = 0; i < list.length; i++) {
        xAxis.push(list[i].date);
        seriesView.push(list[i].viewCount)
        seriesVote.push(list[i].voteCount)
      }

      console.log("____" + seriesView)
      console.log("+++++++" + seriesVote)

      const option1 = {
        title: {
          text: '30天趋势图'
        },
        tooltip: {
          trigger: 'axis'
        },
        grid: {
          left: '1%',
          right: '3%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          show: true,
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          data: xAxis
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {

            name: '总阅读数',
            data: seriesView,
            type: 'line',
            smooth: true
          },
          {

            name: '总点赞量',
            data: seriesVote,
            type: 'line',
            smooth: true
          }

        ]
      };
      // 使用刚指定的配置项和数据显示图表。
      myChart1.setOption(option1);
    }

    const get30Statistic = ()=>{
      axios.get("/ebook-snapshot/statistic-30").then((response)=>{
        const data = response.data;
        if (data.success){
          const statisticList = data.content;
          init30Statistic(statisticList);
        }
      })
    }





    const testEcharts = ()=>{
      // 基于准备好的dom，初始化echarts实例
      const myChart = echarts.init(document.getElementById('main'));

      // 指定图表的配置项和数据
      const option = {
        title: {
          text: 'ECharts 入门示例'
        },
        tooltip: {},
        legend: {
          data: ['销量']
        },
        xAxis: {
          data: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子']
        },
        yAxis: {},
        series: [
          {
            name: '销量',
            type: 'bar',
            data: [5, 20, 36, 10, 10, 20]
          }
        ]
      };

      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    }
    onMounted(()=>{
      getStatistic();
      testEcharts();
      get30Statistic()
    })
    return{
      statisticResp
    }
  }
});
</script>