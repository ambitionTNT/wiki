<template>
  <a-layout>
      <a-layout-sider width="200" style="background: #fff">
      <a-menu
          mode="inline"
          :style="{ height: '100%', borderRight: 0 }"
          @click="handleClick"
      >
        <a-menu-item key="welcome">
            <MailOutlined>

            </MailOutlined>
          <span>
                欢迎
              </span>
        </a-menu-item>
        <a-sub-menu v-for="item in level1"
                    :key="item.id"

        >
          <template  v-slot:title>
                <span>
                  <user-outlined />
                    {{item.name}}
                </span>
          </template>
          <a-menu-item v-for="subItem in item.children"
                       :key="subItem.id"

          >{{subItem.name}}
          </a-menu-item>

        </a-sub-menu>
        <a-menu-item key="tip" :disabled="true">
          <span>以上菜单在分类管理配置</span>
        </a-menu-item>
      </a-menu>
      </a-layout-sider>


      <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
      >
        <div class="welcome" v-show="isShowWelcome">
          <h1>
            <the-welcome></the-welcome>
          </h1>
        </div>


        <a-list  v-show="!isShowWelcome" item-layout="vertical" size="large" grid="{ gutter: 16, column: 4 }"  :data-source="ebooks">
          <template #renderItem="{ item }">
            <a-list-item key="item.name">
              <template #actions>
                  <span >
                    <component :is="'StarOutlined'" style="margin-right: 8px" />
                    {{ item.docCount }}
                  </span>
                <span >
                    <component :is="'UserOutlined'" style="margin-right: 8px" />
                    {{ item.viewCount }}
                  </span>
                <span >
                    <component :is="'LikeOutlined'" style="margin-right: 8px" />
                    {{ item.voteCount }}
                  </span>
              </template>

              <a-list-item-meta :description="item.description">
                <template #title>
                  <router-link :to="'/doc?ebookId=' +item.id">
                    {{item.name}}
                  </router-link>
                </template>
                <template #avatar><a-avatar :src="item.cover" /></template>
              </a-list-item-meta>
            </a-list-item>
          </template>
        </a-list>
      </a-layout-content>
  </a-layout>
</template>



<script lang="ts">
import {defineComponent, onMounted, reactive, ref, toRef} from 'vue';
import HelloWorld from '@/components/HelloWorld.vue'; // @ is an alias to /src
import axios from "axios";
import {LikeOutlined, MessageOutlined, StarOutlined} from "@ant-design/icons-vue";
import {Tool} from "@/util/tool";
import {message} from "ant-design-vue";
import TheWelcome from '@/components/the-welcome.vue';


const listData: any = [];

for (let i = 0; i < 23; i++) {
  listData.push({
    href: 'https://www.antdv.com/',
    title: `ant design vue part ${i}`,
    avatar: 'https://joeschmoe.io/api/v1/random',
    description:
        'Ant Design, a design language for background applications, is refined by Ant UED Team.',
    content:
        'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.',
  });
}


export default defineComponent({


  components: {
    TheWelcome
  },
  name: 'HomeView',
  setup(){
    console.log("setup");
    const ebooks = ref();
    const actions = [
      { icon: StarOutlined, text: '156' },
      { icon: LikeOutlined, text: '156' },
      { icon: MessageOutlined, text: '2' }
    ];
    const ebookList = reactive({books:[]});




    let categoryId2 = 0;
    const handleClick = (value: any) => {
      console.log("111 ",  value.key)
      if (value.key == "welcome"){
        isShowWelcome.value = true;
      }else {
        categoryId2 = value.key;
        isShowWelcome.value = false;
        handleQuery()
      }
    }
    const isShowWelcome = ref(true)
    const level1 = ref()
    level1.value = []
    const categoryIds = ref();
    /**
     * 树形结构 level1
     * [{
     * id: "",
     * name: "",
     * children:[{
     *   id: "",
     *   name: ""
     * }]
     *
     * }]
     */
    let categorys :any;

    const handleQuery = ()=>{
      axios.get("/ebook/list",{
        params:{
          page:1,
          size:100,
          categoryId2: categoryId2
        }
      }).then((response)=>{
        const  data = response.data;
        ebooks.value = data.content.list;
        // ebookList.books = data.content;
        // console.log(response);
      })
    }
    const handleCategory = () => {

      // 如果不清空现有数据，则编辑保存重新加载数据后，再点编辑，则列表显示的还是编辑前的数据

      axios.get("/category/all"
      ).then((response) => {

        const data = response.data;
        if (data.success) {
          categorys = data.content;
          console.log("原始数组: ", categorys)

          level1.value = []
          level1.value = Tool.array2Tree(categorys, 0);
          console.log("树形数据是：", level1 )
          // 加载完分类后，再加载电子书，否则如果分类树加载很慢，则电子书渲染会报错
        } else {
          message.error(data.message);
        }
      });
    }

    onMounted(()=>{
      handleCategory();
      // handleQuery()

    })
    return{
      ebooks,
      listData,
      level1,
      actions,
      handleClick,
      isShowWelcome,

    books : toRef(ebookList, "books"),
      pagination :{
        onChange: (page: number) => {
          console.log(page);
        },
        pageSize: 3,
      }

    }

  }
});
</script>

<style scoped>

.ant-avatar {
  width: 50px;
  height: 50px;
  line-height: 50px;
  border-radius: 8%;
  margin: 5px;

}
</style>
