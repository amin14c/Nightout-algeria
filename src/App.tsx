import React from 'react';
import { Map, MapPin, Smartphone, Download, Github } from 'lucide-react';

export default function App() {
  return (
    <div className="min-h-screen bg-[#0B0C10] text-[#C5C6C7] font-sans selection:bg-[#8B5CF6] selection:text-white p-4 md:p-8" dir="rtl">
      <div className="max-w-4xl mx-auto space-y-12 pb-20">
        
        {/* Header */}
        <header className="text-center space-y-4 pt-10">
          <h1 className="text-4xl md:text-6xl font-extrabold text-transparent bg-clip-text bg-gradient-to-l from-[#8B5CF6] to-[#00E676] pb-2">
            Night Out Algeria
          </h1>
          <p className="text-xl md:text-2xl text-gray-300 font-light">
            التقرير التقني والتجاري (Pitch Deck & Technical Report)
          </p>
          <div className="flex justify-center items-center gap-2 mt-4 text-sm text-gray-400">
            <span className="bg-white/10 px-4 py-1.5 rounded-full border border-white/10 shadow-sm">موجه للمستثمرين والشركاء المحتملين (Investors & Co-founders)</span>
          </div>
        </header>

        {/* 1. Executive Summary */}
        <section className="bg-[#1F2833] rounded-2xl p-6 md:p-8 border border-white/5 shadow-2xl relative overflow-hidden">
            <div className="absolute top-0 right-0 w-2 h-full bg-[#8B5CF6]"></div>
            <h2 className="text-2xl font-bold text-white mb-4 flex items-center gap-3">
            <span className="text-3xl text-[#8B5CF6]">1.</span> الملخص التنفيذي (Executive Summary)
            </h2>
            <p className="text-gray-300 leading-relaxed text-lg">
            <strong>Night Out Algeria</strong> هو تطبيق محمول رائد يهدف إلى إحداث ثورة في كيفية اكتشاف الأفراد للأماكن الترفيهية، المطاعم، والمقاهي في الجزائر. تم تصميم التطبيق كلياً باستخدام أحدث تقنيات <strong>Jetpack Compose</strong> مع تبني نظام تصميم <strong>Material Design 3</strong> ليقدم تجربة مستخدم (UX) فائقة تضاهي التطبيقات العالمية.
            </p>
        </section>

        {/* 2. Mega UI/UX Upgrade Notification */}
        <section className="bg-gradient-to-r from-purple-900/40 to-blue-900/40 border border-purple-500/30 rounded-2xl p-6 md:p-8 mt-6">
            <h2 className="text-xl font-bold text-white mb-4 flex items-center gap-3">
                <span className="text-2xl">✨</span> تحديث تصميمي ضخم (Expert UI/UX Upgrade)
            </h2>
            <p className="text-gray-300 leading-relaxed mb-4">
                استجابة لطلبك بإنشاء تطبيق بنظام تصميم وتجربة مستخدم بمستوى خبراء <strong>(Expert Android UI/UX)</strong>، تم ترقية الكود المصدري للتطبيق <code>(app/src/main/...)</code> ليشمل:
            </p>
            <ul className="list-disc list-inside text-sm text-gray-300 space-y-2 mb-4">
                <li><strong>Material 3 & Dynamic Colors:</strong> تطبيق نظام ألوان كامل متجاوب مع الوضع الفاتح والداكن (Light/Dark mode) وبدعم ألوان النظام (Dynamic Colors).</li>
                <li><strong>Bottom Navigation Bar:</strong> إضافة شريط تنقل سفلي احترافي وسهل الاستخدام.</li>
                <li><strong>Advanced Components:</strong> إضافة <code>LazyRow</code> لعرض الفعاليات المميزة، واستخدام <code>Coil</code> لتحميل الصور بكفاءة وسرعة.</li>
                <li><strong>Animations:</strong> إضافة انتقالات حركية سلسة (Animations) عند تحميل بطاقات الأماكن.</li>
            </ul>
            <p className="text-[#00E676] font-bold text-sm">
                هذه التحديثات قد تم تطبيقها مسبقاً على الكود البرمجي للأندرويد، ويمكنك استخراج تطبيق الـ APK الجديد من GitHub لتجربتها بنفسك!
            </p>
        </section>

        {/* 2. Key Features */}
        <section className="space-y-6">
          <h2 className="text-2xl font-bold text-white mb-4 flex items-center gap-3">
            <span className="text-3xl text-[#00E676]">2.</span> المميزات التنافسية (Key Features)
          </h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <FeatureCard 
              icon="🌍"
              title="تعدد اللغات الآني"
              desc="دعم مدمج للغات (العربية، الفرنسية، والإنجليزية) لتغطية كافة شرائح المجتمع الجزائري بالإضافة إلى السياح الأجانب والمغتربين."
            />
            <FeatureCard 
              icon="🗺️"
              title="خرائط تفاعلية ذكية"
              desc="استخدام تقنيات خرائط مفتوحة المصدر (Osmdroid) توفر تجربة اكتشاف جغرافية احترافية بدون التكاليف التشغيلية الباهظة والفواتير المفاجئة المرتبطة بـ Google Maps."
            />
            <FeatureCard 
              icon="🛡️"
              title="نظام إدارة وتحكم متطور"
              desc="لوحة تحكم (Admin Dashboard) للموافقة على الأماكن المضافة من قبل المستخدمين لضمان جودة المحتوى ومنع العشوائية."
            />
            <FeatureCard 
              icon="⚡"
              title="تجربة مستخدم فائقة (UX)"
              desc="واجهات عصرية (Dark Mode) مصممة خصيصاً لتناسب الشاشات بمختلف أحجامها، مع انتقالات سلسة وتجاوب سريع وفق معايير الجودة العالمية."
            />
          </div>
        </section>

        {/* 3. Technical Stack */}
        <section className="bg-[#1F2833]/50 rounded-2xl p-6 md:p-8 border border-white/5">
          <h2 className="text-2xl font-bold text-white mb-6 flex items-center gap-3">
            <span className="text-3xl text-[#8B5CF6]">3.</span> البنية التقنية (Technical Stack)
          </h2>
          <div className="space-y-6">
            <TechItem 
              title="برمجة الواجهات (Frontend)"
              tech="Android Native / Kotlin / Jetpack Compose / MVVM"
              desc="بعيداً عن الحلول الهجينة البطيئة، تم بناء التطبيق باستخدام بيئة أندرويد الأصلية وأحدث أدوات تقنيات العرض (Jetpack Compose) ونمط هندسة البرمجيات (MVVM). يضمن هذا أفضل استغلال للعتاد (Hardware)، وتقليل استهلاك البطارية، وتجربة سلسة جداً (Smooth Animations)."
            />
            <TechItem 
              title="البنية التحتية الخلفية (Backend API & Database)"
              tech="Firebase (Auth & Firestore NoSQL)"
              desc="قاعدة بيانات سحابية لحظية تعالج التحديثات المتزامنة بكفاءة، مع نظام مصادقة آمن ومدار يدعم إنشاء الحسابات، وتسجيل الدخول، واستعادة كلمات المرور باحترافية."
            />
            <TechItem 
              title="التكامل المدفوع (CI/CD DevOps)"
              tech="GitHub Actions Workflows"
              desc="تم بناء نظام أتمتة كامل لمعالجة العمليات، حيث يتم بناء وتوليد ملفات التطبيق (APK) بشكل سحابي ومؤتمت (Automated Build Pipeline) عند كل تحديث، ما يبسط عملية النشر وإصدار التحديثات اللاحقة للمنتج."
            />
          </div>
        </section>

        {/* 4. Scalability & Business Vision */}
        <section className="bg-gradient-to-br from-[#161836] to-[#0B0C10] rounded-2xl p-6 md:p-8 border border-[#8B5CF6]/30 shadow-xl">
          <h2 className="text-2xl font-bold text-white mb-4 flex items-center gap-3">
            <span className="text-3xl text-white">4.</span> نموذج العمل والتوسع (Scalability & Monetization)
          </h2>
          <p className="text-gray-300 leading-relaxed text-lg mb-6">
            تم تخطيط وهيكلة الكود لتطبيق <strong>Night Out Algeria</strong> ليكون <span className="text-[#00E676] font-bold">Scalable-First</span>، مما يضمن تقليل ديون التطوير (Technical Debt) ويسهل التحول كشركة ناشئة.
          </p>
          <ul className="list-disc leading-loose list-inside text-gray-400 space-y-3">
            <li><strong>تحمل ضغط البيانات:</strong> استخدام بنية Firestore الموزعة عالمياً يتيح للتطبيق التوسع التلقائي (Auto-scaling) لخدمة آلاف المستخدمين والطلبات المتزامنة بصفر انقطاعات.</li>
            <li><strong>الكود المعياري (Modular Architecture):</strong> يسمح بدمج وإضافة ميزات مستقبلية بسهولة قصوى (مثل: أنظمة حجوزات إلكترونية، بوابات الدفع الإلكترونية، العروض المؤقتة).</li>
            <li><strong>فرص تحقيق أرباح (Revenue Streams):</strong> المنصة مجهزة لبناء خطط اشتراكات مدفوعة للمحلات (Premium Features) للظهور أعلى نتائج البحث في الخريطة، بالإضافة لتحليل وتوجيه سلوك المستهلك وإعلانات المنشآت المحلية الموجهة (Targeted Ads).</li>
          </ul>
        </section>

        {/* 5. Screenshots */}
        <section className="bg-[#1F2833]/30 rounded-2xl p-6 md:p-8 border border-white/5 text-center">
          <h2 className="text-2xl font-bold text-white mb-8 flex items-center justify-center gap-3">
             نظرة على الواجهات (Product UI Sneak Peek)
          </h2>
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-8 max-w-2xl mx-auto">
             <div>
                <img src="/src/assets/images/login_screen_mockup_1779000455966.png" alt="Login UX" className="rounded-2xl border border-white/10 shadow-2xl mx-auto" />
                <p className="mt-4 text-gray-400 text-sm">تسجيل الدخول - نظيف وعصري متوافق مع نظام الألوان</p>
             </div>
             <div>
                <img src="/src/assets/images/admin_dashboard_mockup_1779000480308.png" alt="Admin UX" className="rounded-2xl border border-white/10 shadow-2xl mx-auto" />
                <p className="mt-4 text-gray-400 text-sm">واجهة الإدارة - أداة للتحكم في الأنشطة المضافة (CMS)</p>
             </div>
          </div>
        </section>

        {/* Call to Action */}
        <div className="text-center pt-8">
          <div className="inline-block bg-[#00E676]/10 border border-[#00E676]/30 px-6 py-4 rounded-xl shadow-[0_0_20px_rgba(0,230,118,0.1)]">
            <p className="text-[#00E676] font-bold text-lg leading-relaxed">
              هذا المشروع ليس مجرد واجهة وهمية أومقترح على ورق.<br/>إنه منتج برمجي حقيقي (MVP)، متين الأركان، وجاهز للاختبار والانطلاق بالأسواق.
            </p>
          </div>
        </div>

      </div>
    </div>
  );
}

function FeatureCard({ icon, title, desc }: { icon: string, title: string, desc: string }) {
  return (
    <div className="bg-[#1c1e27] p-6 rounded-xl border border-white/5 hover:border-[#8B5CF6]/50 hover:bg-[#161836]/70 transition-all duration-300">
      <div className="text-4xl mb-4">{icon}</div>
      <h3 className="text-xl font-bold text-white mb-2">{title}</h3>
      <p className="text-gray-400 text-sm leading-relaxed">{desc}</p>
    </div>
  );
}

function TechItem({ title, tech, desc }: { title: string, tech: string, desc: string }) {
  return (
    <div className="border-r-4 border-[#8B5CF6] pr-4 space-y-1 bg-white/5 p-4 rounded-l-xl">
      <h4 className="text-lg font-bold text-white">{title}</h4>
      <div className="text-sm font-mono text-[#00E676] mb-2">{tech}</div>
      <p className="text-gray-400 text-sm leading-relaxed">{desc}</p>
    </div>
  );
}

