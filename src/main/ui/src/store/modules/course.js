import {COURSE} from '@/store/mutation-types';
import { getCourseList } from '@/api/course';

const state = {
    list: []
};

const mutations = {
    [COURSE.SET_COURSE_LIST]: (state, list) => {
        state.list = list || [];
    }
};

const actions = {
    getCourses({ commit, state }) {
        return new Promise((resolve, reject) => {
            getCourseList().then(response => {
                const { errno, entities } = response;
                if (errno === 0 && entities) {
                    commit(COURSE.SET_COURSE_LIST, entities);
                }
                resolve(response);
            }).catch(error => {
                // const response = {
                //     "errno":0,
                //     "error":"success",
                //     "total":0,
                //     "entities":[
                //         {
                //             "id":"110f4c30-4c0d-11ec-8f19-fa163e34a620",
                //             "courseName":"匠心筑梦",
                //             "longitude":21.2,
                //             "latitude":11.3,
                //             "pageContent":"坐落于上海市工人文化宫一楼的“上海工匠馆”，讲述了海派工匠的发展历史，展现了百余年来海派工匠的智慧与精湛技艺，是弘扬工匠精神的新高地、引领青年成才的新灯塔。课程通过参观工匠馆，列举大国工匠李斌的事例，来探讨工匠精神。围绕职业道德中爱岗敬业、诚实守信、办事公道、服务群众、奉献社会等核心概念，重点阐释新时代爱岗敬业这一核心内容。",
                //             "isView":false,
                //             "videoUrl":"http://10.119.2.216:8081/video_1_110f4c30-4c0d-11ec-8f19-fa163e34a620.mp4",
                //             "imageUrls":"http://10.119.2.216:8081/img_1_110f4c30-4c0d-11ec-8f19-fa163e34a620.jpeg;http://10.119.2.216:8081/img_2_110f4c30-4c0d-11ec-8f19-fa163e34a620.jpg",
                //             "teacher":"沈全",
                //             "address":"上海工匠馆",
                //             "courseTag":"course3",
                //             "college":"马克思主义学院"
                //         },
                //         {
                //             "id":"192bd0a6-4d05-11ec-8f19-fa163e34a620",
                //             "courseName":"在劳动模范风采中感悟人民群众的创造伟力",
                //             "pageContent":"上海劳动模范风采展陈于上海市总工会一楼，以故事、图片、实物等形式，展现了新中国成立以后由全国总工会和上海市总工会评选出的其中一百五十多位上海市优秀劳模代表的动人事迹。课程依托上海劳动模范风采展，通过参观和问题引导，让学生感悟人民群众是历史的创造者，把握树立唯物主义史观，坚持马克思主义群众观点，贯彻党的群众路线。",
                //             "isView":false,
                //             "videoUrl":"http://10.119.2.216:8081/video_1_192bd0a6-4d05-11ec-8f19-fa163e34a620.mp4",
                //             "imageUrls":"http://10.119.2.216:8081/img_1_192bd0a6-4d05-11ec-8f19-fa163e34a620.jpg;http://10.119.2.216:8081/img_2_192bd0a6-4d05-11ec-8f19-fa163e34a620.jpg;http://10.119.2.216:8081/img_3_192bd0a6-4d05-11ec-8f19-fa163e34a620.jpg",
                //             "teacher":"王成峰",
                //             "address":"《时代领跑者——上海劳动模范风采主题展》展馆",
                //             "courseTag":"course4",
                //             "college":"马克思主义学院"
                //         },
                //         {
                //             "id":"27e22e3b-4dd2-11ec-8f19-fa163e34a620",
                //             "courseName":"坚守育人初心，传承师道使命—从教故事，育人情怀",
                //             "pageContent":"坚守育人初心，传承师德使命是教师终其一生恪守的信念。视频中的三位教师分别荣获上海商学院2016年度、2018年度、2019年度师德标兵。他们立足本岗，为人为师为学，与学生“穷理以致其知，反躬以践其实”，引导培养一代代上商人，从育人小故事中可以感受到育人的大情怀。",
                //             "isView":true,
                //             "videoUrl":"http://10.119.2.216:8081/video_1_27e22e3b-4dd2-11ec-8f19-fa163e34a620.mp4",
                //             "imageUrls":"http://10.119.2.216:8081/img_1_27e22e3b-4dd2-11ec-8f19-fa163e34a620.png",
                //             "teacher":"袁美琴、胡学庆、陈小愚",
                //             "address":"上海商学院",
                //             "courseTag":"course14",
                //             "college":"商务经济学院"
                //         },
                //         {
                //             "id":"35f69387-5253-11ec-8f19-fa163e34a620",
                //             "courseName":"从上海最早的红色保险组织—“保联”的故事看党的群众路线",
                //             "pageContent":"上海是近代中国金融保险业的中心城市，也是中国共产党领导下的红色保险事业的诞生地。课程通过讲述中国最早的红色保险组织—上海市保险业业余联谊会在抗日战争时期坚持群众路线，利用保险业的特性，广泛团结一切可以团结的力量，为抗战胜利做出了积极贡献的故事，使学生对群众路线的内涵、意义和如何坚持群众路线有更深刻的认识。",
                //             "isView":false,
                //             "videoUrl":"http://10.119.2.216:8081/video_1_35f69387-5253-11ec-8f19-fa163e34a620.mp4",
                //             "imageUrls":"",
                //             "teacher":"贺然",
                //             "address":"上海财经大学商学博物馆保险馆",
                //             "courseTag":"course13",
                //             "college":"马克思主义学院"
                //         },
                //         {
                //             "id":"3a88c653-4d07-11ec-8f19-fa163e34a620",
                //             "courseName":"隐蔽战线之无声较量",
                //             "pageContent":"在中国共产党百年华诞之年，课程依托中共上海地下组织斗争史陈列馆，实地讲授为实现社会主义和共产主义理想而奋斗的斗争历史。结合中共上海地下党在抗日战争期间、解放战争时期、上海解放中的丰富史料，使学生具象的感知上海是座初心之城、光荣之城，中国共产党从这里诞生，中国共产党人从这里出征，中国共产党的历史从这里开始。",
                //             "isView":false,
                //             "videoUrl":"http://10.119.2.216:8081/video_1_3a88c653-4d07-11ec-8f19-fa163e34a620.mp4",
                //             "imageUrls":"http://10.119.2.216:8081/img_1_3a88c653-4d07-11ec-8f19-fa163e34a620.png;http://10.119.2.216:8081/img_2_3a88c653-4d07-11ec-8f19-fa163e34a620.jpg",
                //             "teacher":"施阳九",
                //             "address":"中共上海地下组织斗争史陈列馆",
                //             "courseTag":"course5",
                //             "college":"马克思主义学院"
                //         },
                //         {
                //             "id":"3ee9fa5e-4dcf-11ec-8f19-fa163e34a620",
                //             "courseName":"初心与使命：上商创校往事",
                //             "pageContent":"课程展示了上海商学院创校时期的老照片、老校徽、老报纸，讲述当年创校往事。七十多年前艰苦创校时，上海商学院就已经打上了红色的烙印，根植了红色的基因。老报纸见证着创校前辈艰苦奋斗的历史，老校徽上“忠诚”“朴实”“廉洁”“勤能”八个大字铭刻着学校“为党育人、为国育才”的初心和使命。",
                //             "isView":true,
                //             "videoUrl":"http://10.119.2.216:8081/video_1_3ee9fa5e-4dcf-11ec-8f19-fa163e34a620.mp4",
                //             "imageUrls":"http://10.119.2.216:8081/img_2_3ee9fa5e-4dcf-11ec-8f19-fa163e34a620.jpeg",
                //             "teacher":"李强",
                //             "address":"中央税务学校华东分校",
                //             "courseTag":"course11",
                //             "college":"文法学院"
                //         },
                //         {
                //             "id":"441e5e50-4dcd-11ec-8f19-fa163e34a620",
                //             "courseName":"中国现代金融的起源与发展",
                //             "pageContent":"上海是中国近代银行的发祥地。160年的上海金融史，就是中国现代金融的起源、发展的缩影。课程依托银行博物馆，讲授着重从早期钱庄业的繁荣为近代史的发端，到外资金融机构的抢滩进驻，华资金融机构的兴起发展以及远东金融中心的地位确立等几个历史上的转折点，以古鉴今探究上海作为全球最具潜力的新兴金融市场的历史进程。",
                //             "isView":false,
                //             "videoUrl":"http://10.119.2.216:8081/video_1_441e5e50-4dcd-11ec-8f19-fa163e34a620.mp4",
                //             "imageUrls":"http://10.119.2.216:8081/img_1_441e5e50-4dcd-11ec-8f19-fa163e34a620.jpg;http://10.119.2.216:8081/img_2_441e5e50-4dcd-11ec-8f19-fa163e34a620.jpg",
                //             "teacher":"李志刚",
                //             "address":"上海市银行博物馆",
                //             "courseTag":"course8",
                //             "college":"财务金融学院"
                //         },
                //         {
                //             "id":"44dabd32-4d08-11ec-8f19-fa163e34a620",
                //             "courseName":"中共四大与早期合作社运动",
                //             "pageContent":"1925年1月11日至22日，中国共产党第四次全国代表大会在上海召开。大会总结了建党以来的经验，第一次明确提出无产阶级在民主革命中的领导权和工农联盟问题。课程以讲授党提出大力兴办合作社运动为重点，总结党在此次会议中革命理论和革命策略上的重大突破。初步形成的新民主主义革命的基本思想，为随后出现的革命新高潮做了思想上、理论上和政策上的准备。",
                //             "isView":false,
                //             "videoUrl":"http://10.119.2.216:8081/video_1_44dabd32-4d08-11ec-8f19-fa163e34a620.mp4",
                //             "imageUrls":"http://10.119.2.216:8081/img_1_44dabd32-4d08-11ec-8f19-fa163e34a620.jpg;http://10.119.2.216:8081/img_2_44dabd32-4d08-11ec-8f19-fa163e34a620.jpg;http://10.119.2.216:8081/img_3_44dabd32-4d08-11ec-8f19-fa163e34a620.jpg;http://10.119.2.216:8081/img_4_44dabd32-4d08-11ec-8f19-fa163e34a620.jpg",
                //             "teacher":"付清海",
                //             "address":"中共“四大”纪念馆",
                //             "courseTag":"course6",
                //             "college":"马克思主义学院"
                //         },
                //         {
                //             "id":"53ad8eab-4dce-11ec-8f19-fa163e34a620",
                //             "courseName":"税收的作用与改革",
                //             "pageContent":"上海（国际）纳税服务中心是集纳税资讯沟通渠道、服务方式创新基地、税法宣传前沿阵地等多功能的国际化高端纳税服务平台。课程结合实地走访，从财政支出角度阐述了税收是财政收入的重要来源，针对我国不同时期的经济形势，讲授了税收政策的变化和特性，论述税收的作用是为了满足公共需求，取之于民而用之于民。",
                //             "isView":false,
                //             "videoUrl":"http://10.119.2.216:8081/video_1_53ad8eab-4dce-11ec-8f19-fa163e34a620.mp4",
                //             "imageUrls":"http://10.119.2.216:8081/img_1_53ad8eab-4dce-11ec-8f19-fa163e34a620.jpg;http://10.119.2.216:8081/img_2_53ad8eab-4dce-11ec-8f19-fa163e34a620.jpg",
                //             "teacher":"万嘉伟",
                //             "address":"国家税务总局12366上海（国际）纳税服务中心",
                //             "courseTag":"course10",
                //             "college":"商务经济学院"
                //         },
                //         {
                //             "id":"59992d86-4dcc-11ec-8f19-fa163e34a620",
                //             "courseName":"统一财经，打赢经济战",
                //             "pageContent":"课程结合陈云同志作为中国社会主义经济建设的开创者和奠基人之一的生平经历，讲授了中国人民解放事业的开展和成功，我国社会主义制度的建立和巩固以及改革开放和社会主义现代化事业的开创和发展。",
                //             "isView":false,
                //             "videoUrl":"http://10.119.2.216:8081/video_1_59992d86-4dcc-11ec-8f19-fa163e34a620.mp4",
                //             "imageUrls":"",
                //             "teacher":"周敏晖",
                //             "address":"陈云纪念馆博物馆",
                //             "courseTag":"course9",
                //             "college":"马克思主义学院"
                //         },
                //         {
                //             "id":"628fef67-4d09-11ec-8f19-fa163e34a620",
                //             "courseName":"金融初心，人民本位",
                //             "pageContent":"课程通过在银行博物馆的实景教学，从珍贵的文物、照片、文献中由点及面，多角度、全景式地讲授中国百年民族金融的变迁图景，鲜活展示中国经济的沧桑巨变。中国共产党在革命和建设时期，在推动金融发展过程中始终秉持服务人民的基本宗旨。党的成长与壮大、革命的胜利与发展，都与群众路线息息相关。",
                //             "isView":false,
                //             "videoUrl":"http://10.119.2.216:8081/video_1_628fef67-4d09-11ec-8f19-fa163e34a620.mp4",
                //             "imageUrls":"http://10.119.2.216:8081/img_1_628fef67-4d09-11ec-8f19-fa163e34a620.png;http://10.119.2.216:8081/img_2_628fef67-4d09-11ec-8f19-fa163e34a620.png",
                //             "teacher":"张小红",
                //             "address":"上海市银行博物馆",
                //             "courseTag":"course7",
                //             "college":"马克思主义学院"
                //         },
                //         {
                //             "id":"6309bc6e-4dd0-11ec-8f19-fa163e34a620",
                //             "courseName":"中国金融的红色基因",
                //             "pageContent":"课程探索中国金融中的红色基因。从新民主主义革命初期，到井冈山革命根据地时期，长征至解放时期以及新中国成立后，在不同历史时期金融与社会变革相辅相成。课程通过实地讲授，使学生更加深刻理解“金融是国家重要的核心竞争力“这一重要论述。",
                //             "isView":false,
                //             "videoUrl":"http://10.119.2.216:8081/video_1_6309bc6e-4dd0-11ec-8f19-fa163e34a620.mp4",
                //             "imageUrls":"http://10.119.2.216:8081/img_1_6309bc6e-4dd0-11ec-8f19-fa163e34a620.jpg;http://10.119.2.216:8081/img_2_6309bc6e-4dd0-11ec-8f19-fa163e34a620.jpg;http://10.119.2.216:8081/img_3_6309bc6e-4dd0-11ec-8f19-fa163e34a620.jpg;http://10.119.2.216:8081/img_4_6309bc6e-4dd0-11ec-8f19-fa163e34a620.jpg",
                //             "teacher":"王小平",
                //             "address":"上海科技金融博物馆",
                //             "courseTag":"course12",
                //             "college":"财务金融学院"
                //         },
                //         {
                //             "id":"a18dc8a7-45ed-11ec-8f19-fa163e34a620",
                //             "courseName":"发展中的中共证券事业",
                //             "longitude":123.453534,
                //             "latitude":211.324324,
                //             "pageContent":"中国证券博物馆于2018年1月经中央编办批复成立，是我国证券行业唯一一家国家级博物馆。馆内藏品丰富，以改革开放以来证券期货业发展的当代藏品为主，见证了新中国证券市场的诞生、发育和成长。课程借助馆内藏品，透过证券业在中国沉沉浮浮的历史画卷，帮助大家一起来体会马克思主义理论的一个重要特征—马克思主义的发展性。",
                //             "isView":false,
                //             "videoUrl":"http://10.119.2.216:8081/video_1_a18dc8a7-45ed-11ec-8f19-fa163e34a620.mp4",
                //             "imageUrls":"http://10.119.2.216:8081/img_1_a18dc8a7-45ed-11ec-8f19-fa163e34a620.jpg;http://10.119.2.216:8081/img_2_a18dc8a7-45ed-11ec-8f19-fa163e34a620.jpg",
                //             "teacher":"姚志鹏",
                //             "address":"中国证券博物馆",
                //             "courseTag":"course1",
                //             "college":"马克思主义学院"
                //         },
                //         {
                //             "id":"ad744b14-45ed-11ec-8f19-fa163e34a620",
                //             "courseName":"党章诞生地话党章中的经济思想",
                //             "longitude":231.43534,
                //             "latitude":213.435433,
                //             "pageContent":"我国已成为世界第二大经济体，成为推动世界经济增长的主要动力源。党的经济纲领是党章核心内容的重要组成部分，体现了党的经济理念、理想与追求，中国共产党党章中的经济思想关系着中国现代化建设的成败。课程是在中国共产党第一个党章的诞生地—中共二大会址，向大家讲授从党的一大到十九大，历次党章中的主要的经济思想。进而，从党的非凡历程中，感悟一代代共产党人的初心和使命。",
                //             "isView":false,
                //             "videoUrl":"http://10.119.2.216:8081/video_1_ad744b14-45ed-11ec-8f19-fa163e34a620.mp4",
                //             "imageUrls":"http://10.119.2.216:8081/img_1_ad744b14-45ed-11ec-8f19-fa163e34a620.jpg;http://10.119.2.216:8081/img_2_ad744b14-45ed-11ec-8f19-fa163e34a620.jpg;http://10.119.2.216:8081/img_3_ad744b14-45ed-11ec-8f19-fa163e34a620.jpg",
                //             "teacher":"李新亮",
                //             "address":"中共二大会址",
                //             "courseTag":"course2",
                //             "college":"马克思主义学院"
                //         }
                //     ]
                // }
                // const { errno, entities } = response;
                // if (errno === 0 && entities) {
                //     commit(COURSE.SET_COURSE_LIST, entities);
                // }
                // resolve(response);
                reject(error);
            });
        });
    },
};

export default {
    namespaced: true,
    state,
    mutations,
    actions
};
